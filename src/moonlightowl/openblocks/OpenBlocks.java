package moonlightowl.openblocks;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import moonlightowl.openblocks.io.JSON;
import moonlightowl.openblocks.io.lua.Lua;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Joint;
import moonlightowl.openblocks.structure.Wire;
import moonlightowl.openblocks.ui.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * OpenBlocks.OpenBlocks
 * Created by MoonlightOwl on 10/25/15.
 * ===
 * Entry point and main window
 */

public class OpenBlocks extends Application {
    private Stage parentStage;
    // FXML links
    public AnchorPane rootPane;
    public MenuBar menuBar;
    public HBox toolBar;
    public ScrollPane scroller;
    // Custom elements
    private ToolPane[] tools;
    private Progress progress;
    private About about;
    private Workspace workspace;

    private boolean selectedTrash = false, selectionEdit = false;
    private Blocks.Id selected;
    private ImageView selectedIcon;
    private ArrayList<Wire> wires;
    private boolean addWires = false;

    private File projectFile;
    private boolean changed = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        parentStage = primaryStage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
        loader.setController(this);
        loader.load();

        // Resources
        Assets.load();
        Log.init();

        // Generate GUI
        initUI();

        // Create IDE window
        setTitle(Settings.UNTITLED);
        primaryStage.setScene(new Scene(rootPane, Settings.WIDTH, Settings.HEIGHT));
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(400);
        primaryStage.show();

        // Handle exit
        primaryStage.setOnCloseRequest(event -> {
            if(changed) {
                // Ask a question
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Завершение работы");
                alert.setHeaderText("Сохранение изменений");
                alert.setContentText("В проект были внесены изменения. Сохранить?");

                ButtonType buttonSave = new ButtonType("Сохранить");
                ButtonType buttonDiscard = new ButtonType("Не сохранять");
                ButtonType buttonCancel = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(buttonSave, buttonDiscard, buttonCancel);

                // Get an answer
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonSave){
                    saveProject();
                } else if (result.get() == buttonCancel) {
                    event.consume();
                }
            }

            // Finalize resources
            if(!event.isConsumed()){
                Log.out("Shutdown.");
                Log.close();
            }
        });

        Log.out("Let's go!");
    }
    public static void main(String[] args) {
        launch(args);
    }


    public void setTitle(String title){
        if(title != null)
            parentStage.setTitle(title + " - " + Settings.TITLE + " " + Settings.VERSION);
        else
            parentStage.setTitle(Settings.TITLE + " " + Settings.VERSION);
        changed = false;
    }
    public void projectChanged() {
        if(!changed){
            parentStage.setTitle("*"+parentStage.getTitle());
            changed = true;
        }
    }


    public void initUI(){
        progress = new Progress(parentStage);
        about = new About(parentStage);
        workspace = new Workspace(scroller);

        selectedIcon = new ImageView();
        selectedIcon.setScaleX(0.3);
        selectedIcon.setScaleY(0.3);
        rootPane.getChildren().add(selectedIcon);

        initToolsPanels();
        initToolBar();

        wires = new ArrayList<>();

        registerListeners();
    }

    /** Event listenters */
    private void registerListeners() {
        // Wire operations
        Joint.setOnClickListenter(event ->{
            Joint joint = (Joint)event.getSource();
            if(event.getButton() == MouseButton.PRIMARY) {
                // Replace existing one
                if(joint.isAttached()){
                    Wire[] old = new Wire[]{};
                    if(!joint.isMultiwired() || wires.isEmpty()){
                        old = joint.getWires();
                        joint.detachAllWires();
                    }
                    if(!addWires) {
                        wires.forEach(joint::attachWire);
                        wires.clear();
                    }
                    wires.addAll(Arrays.asList(old));
                }
                // Or create / attach new wire
                else {
                    if (wires.isEmpty()) {
                        Wire wire = new Wire();
                        joint.attachWire(wire);
                        wire.reposition(joint.getAbsX(), joint.getAbsY());
                        workspace.addWire(wire);
                        wires.add(wire);
                    } else {
                        wires.forEach(joint::attachWire);
                        wires.clear();
                    }
                }
                projectChanged();
            }
        });

        // Block removement
        Block.setOnClickListenter(event -> {
            if (selectedTrash && event.getButton() == MouseButton.PRIMARY) {
                Block block = (Block)event.getSource();
                workspace.removeBlock(block);
                projectChanged();
            }
        });

        // Wire removement
        Wire.setOnClickListenter(event -> {
            if (selectedTrash && event.getButton() == MouseButton.PRIMARY) {
                Wire wire = (Wire)event.getSource();
                workspace.removeWire(wire);
                projectChanged();
            }
        });

        // Mouse clicks on workspace
        rootPane.setOnMouseClicked(event -> {
            if(hasOpenedPanes())
                closeAllToolPanes();
            else if(event.getButton() == MouseButton.PRIMARY) {
                // Place new block
                if(selected != null) {
                    Block block = selected.getInstance()
                            .setPosition(workspace.projectX(event.getX()),
                                    workspace.projectY(event.getY()));
                    workspace.addBlock(block);
                    projectChanged();
                }
            } else if(event.getButton() == MouseButton.SECONDARY) {
                // Deselect current block type
                deselect();
                // Remove current uncomplete wire
                if(!wires.isEmpty()){
                    wires.forEach(workspace::removeWire);
                    wires.clear();
                }
            }
        });
        rootPane.setOnMousePressed(event -> {
            if(event.getButton() == MouseButton.PRIMARY) {
                // If there is nothing else to do - change selection
                if (!hasOpenedPanes() && selected == null) {
                    selectionEdit = true;
                    workspace.setSelectionVisible(false);
                    workspace.setSelectionX1(workspace.projectX(event.getX()));
                    workspace.setSelectionY1(workspace.projectY(event.getY()));
                }
            }
        });
        rootPane.setOnMouseReleased(event -> {
            if(selectionEdit) {
                selectionEdit = false;
            }
        });

        // Move mouse tool icon & current wire loose end (if any)
        rootPane.setOnMouseMoved(event -> {
            selectedIcon.setTranslateX(event.getSceneX());
            selectedIcon.setTranslateY(event.getSceneY());
            if(!wires.isEmpty())
                for(Wire wire: wires)
                    wire.reposition(workspace.projectX(event.getX()),
                            workspace.projectY(event.getY()));
            if(selectionEdit) {
                if(!workspace.isSelectionVisible()) workspace.setSelectionVisible(true);
                workspace.setSelectionX2(workspace.projectX(event.getX()));
                workspace.setSelectionY2(workspace.projectY(event.getY()));
            }
        });
        //rootPane.setOnMouseDragged(event -> rootPane.getOnMouseMoved().handle(event));
        rootPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, rootPane.getOnMouseMoved());

        // Keyboard tool selector
        rootPane.setOnKeyPressed(event -> {
            switch(event.getCode()){
                case DIGIT1: toggleToolPane(0); break;
                case DIGIT2: toggleToolPane(1); break;
                case DIGIT3: toggleToolPane(2); break;
                case DIGIT4: toggleToolPane(3); break;
                case S: workspace.drag(0, -Settings.DRAG_SPEED); break;
                case W: workspace.drag(0, Settings.DRAG_SPEED); break;
                case D: workspace.drag(-Settings.DRAG_SPEED, 0); break;
                case A: workspace.drag(Settings.DRAG_SPEED, 0); break;
                case SHIFT: addWires = true; break;
                case DELETE: if(workspace.isSelectionVisible()) clearSelection();
                    else selectTrashTool(); break;
                case ESCAPE: deselect(); break;
            }
        });
        rootPane.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case SHIFT: addWires = false;
            }
        });
    }

    /** UI generation */
    private void initToolsPanels(){
        tools = new ToolPane[] {
                new ToolPane("Точки входа"),
                new ToolPane("Действия"),
                new ToolPane("Циклы"),
                new ToolPane("Логика")
        };
        for(Blocks.Id id: Blocks.Id.values()){
            ToolButton tool = new ToolButton(id.name, Assets.toolIcons[id.id]);
            tool.setOnMouseClicked(event -> { select(id); closeAllToolPanes(); });
            tools[id.category.ordinal()].add(tool);
        }
        for(ToolPane pane: tools) rootPane.getChildren().add(pane);
    }
    private void initToolBar(){
        for(int c = 0; c < 4; c++) {
            Button button = newToolBarButton(Assets.toolBarIcon[c+1]);
            int id = c; button.setOnAction(event -> toggleToolPane(id));
            toolBar.getChildren().add(button);
        }
        Button trash = newToolBarButton(Assets.toolBarIcon[5]);
        trash.setOnAction(event -> selectTrashTool());
        toolBar.getChildren().add(trash);
    }
    private Button newToolBarButton(Image image){
        Button button = new Button();
        button.setId("tool");
        button.setGraphic(new ImageView(image));
        return button;
    }

    /** Tool actions */
    public void selectTrashTool(){
        deselect();
        selectedTrash = true;
        selectedIcon.setImage(Assets.toolBarIcon[5]);
    }
    public void select(Blocks.Id id){
        deselect();
        selected = id;
        selectedIcon.setImage(Assets.toolIcons[id.id]);
    }
    public void deselect(){
        selected = null;
        selectedTrash = false;
        selectedIcon.setImage(null);
        selectionEdit = false;
        workspace.setSelectionVisible(false);
    }

    /** Block panel actions */
    public void closeAllToolPanes(){ toggleToolPane(-1); }
    public void toggleToolPane(int id){
        for(int c = 0; c < tools.length; c++)
            if(c == id) tools[c].toggle();
            else tools[c].close();
    }
    public boolean hasOpenedPanes(){ for(ToolPane pane: tools) if(pane.isOpen()) return true; return false; }


    /** Project file management */
    private void save() {
        String data = JSON.generate(workspace).toJSONString();
        if(projectFile != null) {
            try (FileWriter writer = new FileWriter(projectFile)) {
                writer.write(data);
                Log.out("Successfully saved JSON Object to file...");
            } catch (IOException e) {
                Log.error("Project saving error", e);
                error("Ошибка записи проекта",
                        "В силу неведомых причин, сериализация проекта в JSON прошла неудачно.\n" +
                                "Проверьте, есть ли свободное место на диске, и имеет ли программа права на запись" +
                                "в выбранном каталоге.", e);
            }
        } else {
            error("Ошибка записи проекта", "Не удалось выбрать имя файла", null);
        }
    }
    private void load() {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(projectFile)) {
            JSONObject data = (JSONObject) parser.parse(reader);
            JSON.recreate(workspace, data);
        } catch (Exception e) {
            Log.error("Error loading project", e);
            error("Ошибка открытия проекта",
                    "Вероятнее всего, файл, который вы пытаетесь открыть - поврежден.", e);
        }
    }

    /** Menu actions */
    public void newProject() {
        workspace.clear();
        projectFile = null;
        setTitle(Settings.UNTITLED);
        deselect(); // Drop all previously selected tools
    }
    public void openProject(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Открыть...");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setSelectedExtensionFilter(
                new FileChooser.ExtensionFilter("Проект "+Settings.TITLE, "*."+Settings.EXTENSION));
        projectFile = fileChooser.showOpenDialog(parentStage);
        if(projectFile != null) {
            workspace.clear();
            load();
            setTitle(projectFile.getName());
            deselect(); // Drop all previously selected tools
        }
    }
    public void saveProject() {
        if(projectFile == null) saveProjectAs();
        else{ save(); setTitle(projectFile.getName()); }
    }
    public void saveProjectAs() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить как...");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setInitialFileName(projectFile == null ?
                Settings.UNTITLED + "." + Settings.EXTENSION : projectFile.getName());
        fileChooser.setSelectedExtensionFilter(
                new FileChooser.ExtensionFilter("Проект "+Settings.TITLE, "*."+Settings.EXTENSION));
        projectFile = fileChooser.showSaveDialog(parentStage);
        if(projectFile != null) {
            save();
            setTitle(projectFile.getName());
        }
    }
    public void exportProject() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Экспортировать в...");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setInitialFileName((projectFile == null ?
                Settings.UNTITLED : projectFile.getName().split("\\.")[0]) + ".lua");
        fileChooser.setSelectedExtensionFilter(
                new FileChooser.ExtensionFilter("Программа OpenComputers", "*.lua"));
        File file = fileChooser.showSaveDialog(parentStage);

        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() throws InterruptedException {
                try (FileOutputStream stream = new FileOutputStream(file)) {
                    if(!Lua.export(workspace, stream))
                        error("Ошибка экспорта",
                                "Внезапно, структура проекта не поддается экпорту! \nОтправьте проект автору IDE, пусть тоже удивится.", null);
                    else {
                        Log.out("Successfully exported project to Lua");
                    }
                } catch (IOException e) {
                    Log.error("Cannot export project properly! Some errors occured.", e);
                    error("Ошибка записи проекта",
                            "В силу неизвестных причин, произошла ошибка записи экспортированного листинга в файл.", e);
                }
                return null;
            }
        };
        task.setOnSucceeded(event -> {
            progress.hide();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Операция завершена");
            alert.setHeaderText("Экспорт в Lua");
            alert.setContentText("Программа успешно экспортирована");
            alert.showAndWait();
        });

        progress.show();
        Thread thread = new Thread(task);
        thread.start();
    }

    public void clearSelection() {
        workspace.clearSelection();
    }

    public void clearProject(){
        workspace.clear();
        projectChanged();
        deselect(); // Drop all previously selected tools
    }

    public void showAboutWindow() {
        about.show();
    }

    public void exit() {
        parentStage.fireEvent(new WindowEvent(parentStage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    /** Messages */
    public void error(String title, String message, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText("Упс! Что-то пошло не так...");

        Label l = new Label(message);
        alert.getDialogPane().setContent(l);

        if(e != null) {
            // Create expandable Exception.
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String exceptionText = sw.toString();

            Label label = new Label("Стектрейс:");

            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);

            alert.getDialogPane().setExpandableContent(expContent);

            // Dirty hack for proper resizing
            alert.getDialogPane().expandedProperty().addListener((event) -> {
                Platform.runLater(() -> {
                    alert.getDialogPane().requestLayout();
                    Stage stage = (Stage)alert.getDialogPane().getScene().getWindow();
                    stage.sizeToScene();
                });
            });
        }

        alert.showAndWait();
    }
}