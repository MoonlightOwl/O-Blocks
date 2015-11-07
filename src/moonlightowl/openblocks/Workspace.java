package moonlightowl.openblocks;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.ui.About;
import moonlightowl.openblocks.ui.ToolButton;
import moonlightowl.openblocks.ui.ToolPane;
import moonlightowl.openblocks.ui.ZoomPane;

import java.util.Random;

/**
 * OpenBlocks.Workspace
 * Created by MoonlightOwl on 10/25/15.
 * ===
 * Data and events processing
 */

public class Workspace {
    public Stage parentStage;
    // FXML links
    public AnchorPane rootPane;
    public MenuBar menuBar;
    public HBox toolBar;
    public ScrollPane scroller;
    // Custom elements
    public ToolPane[] tools;
    public About about;
    private ZoomPane zoomPane;

    private boolean selectedWire = false;
    private boolean selectedTrash = false;
    private Blocks.Id selected;
    private ImageView selectedIcon;

    public void init(Stage parent){
        parentStage = parent;
        about = new About(parentStage);

        zoomPane = new ZoomPane(scroller);
        selectedIcon = new ImageView();
        selectedIcon.setScaleX(0.3);
        selectedIcon.setScaleY(0.3);
        rootPane.getChildren().add(selectedIcon);

        initToolsPanels();
        initToolBar();

        Random random = new Random(System.currentTimeMillis());

        rootPane.setOnMouseClicked(event -> {
            if(hasOpenedPanes())
                closeAllToolPanes();
            else
                if(event.getButton() == MouseButton.PRIMARY) {
                    if(selected != null) {
                        Block block = selected.getInstance().setPosition(zoomPane.projectX(event.getX() - rootPane.getWidth() / 2),
                                zoomPane.projectY(event.getY() - rootPane.getHeight() / 2));
                        zoomPane.add(block);
                    }
                } else if(event.getButton() == MouseButton.SECONDARY) deselect();
        });
        rootPane.setOnMouseMoved(event -> {
            selectedIcon.setTranslateX(event.getSceneX());
            selectedIcon.setTranslateY(event.getSceneY());
        });
        rootPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, rootPane.getOnMouseMoved());
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
        Button wires = newToolBarButton(Assets.toolBarIcon[0]);
        wires.setOnAction(event -> selectWireTool());
        toolBar.getChildren().add(wires);
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
    public void selectWireTool(){
        deselect();
        selectedWire = true;
        selectedIcon.setImage(Assets.toolBarIcon[0]);
    }
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
        selectedWire = false;
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

    /** Windows actions */
    public void showAboutWindow() {
        about.show();
    }
    public void exit() {
        Platform.exit();
    }
}
