package moonlightowl.openblocks;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Joint;
import moonlightowl.openblocks.structure.Wire;
import moonlightowl.openblocks.ui.About;
import moonlightowl.openblocks.ui.ToolButton;
import moonlightowl.openblocks.ui.ToolPane;

/**
 * OpenBlocks.OpenBlocks
 * Created by MoonlightOwl on 10/25/15.
 * ===
 * Entry point and main window
 */

public class OpenBlocks extends Application {
    public Stage parentStage;
    // FXML links
    public AnchorPane rootPane;
    public MenuBar menuBar;
    public HBox toolBar;
    public ScrollPane scroller;
    // Custom elements
    public ToolPane[] tools;
    public About about;
    private Workspace workspace;

    private boolean selectedTrash = false;
    private Blocks.Id selected;
    private ImageView selectedIcon;
    private Wire wire;

    @Override
    public void start(Stage primaryStage) throws Exception {
        parentStage = primaryStage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
        loader.setController(this);
        loader.load();

        // Resources
        Assets.load();

        // Generate GUI
        initUI();

        // Create IDE window
        primaryStage.setTitle("OpenBlocks " + Settings.VERSION);
        primaryStage.setScene(new Scene(rootPane, Settings.WIDTH, Settings.HEIGHT));
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(400);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

    public void initUI(){
        about = new About(parentStage);
        workspace = new Workspace(scroller);

        selectedIcon = new ImageView();
        selectedIcon.setScaleX(0.3);
        selectedIcon.setScaleY(0.3);
        rootPane.getChildren().add(selectedIcon);

        initToolsPanels();
        initToolBar();

        /** Event listenters */
        // Wire operations
        Joint.setOnClickListenter(event ->{
            Joint joint = (Joint)event.getSource();
            if(event.getButton() == MouseButton.PRIMARY) {
                // Replace existing one
                if(joint.isAttached()){
                    Wire old = joint.getWire();
                    joint.attachWire(wire);
                    wire = old;
                    // Or create / attach new wire
                } else {
                    if (wire == null) {
                        wire = new Wire();
                        joint.attachWire(wire);
                        wire.reposition(joint.getAbsX(), joint.getAbsY());
                        workspace.addWire(wire);
                    } else {
                        joint.attachWire(wire);
                        wire = null;
                    }
                }
            }
        });
        // Block removement
        Block.setOnClickListenter(event -> {
            if (selectedTrash) {
                Block block = (Block)event.getSource();
                workspace.removeBlock(block);
            }
        });
        // Wire removement
        Wire.setOnClickListenter(event -> {
            if (selectedTrash) {
                Wire wire = (Wire)event.getSource();
                workspace.removeWire(wire);
            }
        });

        rootPane.setOnMouseClicked(event -> {
            if(hasOpenedPanes())
                closeAllToolPanes();
            else
            if(event.getButton() == MouseButton.PRIMARY) {
                if(selected != null) {
                    Block block = selected.getInstance()
                            .setPosition(workspace.projectX(event.getX()),
                                    workspace.projectY(event.getY()));
                    workspace.addBlock(block);
                }
            } else if(event.getButton() == MouseButton.SECONDARY) {
                // Deselect current block type
                deselect();
                // Remove current uncomplete wire
                if(wire != null){
                    workspace.removeWire(wire);
                    wire = null;
                }
            }
        });

        // Move mouse tool icon & current wire loose end (if any)
        rootPane.setOnMouseMoved(event -> {
            selectedIcon.setTranslateX(event.getSceneX());
            selectedIcon.setTranslateY(event.getSceneY());
            if(wire != null) wire.reposition(workspace.projectX(event.getX()),
                    workspace.projectY(event.getY()));
        });
        rootPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, rootPane.getOnMouseMoved());

        // Keyboard tool selector
        rootPane.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.DIGIT1) toggleToolPane(0);
            if(event.getCode() == KeyCode.DIGIT2) toggleToolPane(1);
            if(event.getCode() == KeyCode.DIGIT3) toggleToolPane(2);
            if(event.getCode() == KeyCode.DIGIT4) toggleToolPane(3);
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
    }

    /** Block panel actions */
    public void closeAllToolPanes(){ toggleToolPane(-1); }
    public void toggleToolPane(int id){
        for(int c = 0; c < tools.length; c++)
            if(c == id) tools[c].toggle();
            else tools[c].close();
    }
    public boolean hasOpenedPanes(){ for(ToolPane pane: tools) if(pane.isOpen()) return true; return false; }


    /** Menu actions */
    public void newProject(ActionEvent actionEvent) {
        workspace.clear();
    }

    public void showAboutWindow() {
        about.show();
    }

    public void exit() {
        Platform.exit();
    }
}