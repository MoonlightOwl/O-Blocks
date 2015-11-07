package moonlightowl.openblocks;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.robot.Start;
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

    private Blocks.Id selected;
    private ImageView selectedIcon;

    public void init(Stage parent){
        parentStage = parent;
        about = new About(parentStage);

        zoomPane = new ZoomPane(scroller);
        selectedIcon = new ImageView();
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
        for(int c=0; c<4; c++)
            toolBar.getChildren().add(newToolBarButton(c));
    }
    private Button newToolBarButton(int id){
        Button button = new Button();
        button.setId("tool");
        button.setGraphic(new ImageView(Assets.toolBarIcon[id]));
        button.setOnAction(event -> toggleToolPane(id));
        return button;
    }

    /** Workscape actions */
    public void select(Blocks.Id id){
        selected = id;
        selectedIcon.setImage(Assets.toolIcons[id.id]);
    }
    public void deselect(){
        selected = null;
        selectedIcon.setImage(null);
    }

    public void closeAllToolPanes(){ toggleToolPane(-1); }
    public void toggleToolPane(int id){
        for(int c = 0; c < tools.length; c++)
            if(c == id) tools[c].toggle();
            else tools[c].close();
    }

    public boolean hasOpenedPanes(){ for(ToolPane pane: tools) if(pane.isOpen()) return true; return false; }

    public void showAboutWindow() {
        about.show();
    }
    public void exit() {
        Platform.exit();
    }
}
