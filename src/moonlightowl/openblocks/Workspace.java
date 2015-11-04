package moonlightowl.openblocks;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import moonlightowl.openblocks.ui.About;
import moonlightowl.openblocks.ui.ToolButton;
import moonlightowl.openblocks.ui.ToolPane;
import moonlightowl.openblocks.ui.ZoomPane;

/**
 * OpenBlocks.Workspace
 * Created by MoonlightOwl on 10/25/15.
 * ===
 * Data and events processing
 */

public class Workspace {
    public Stage parentStage;
    public AnchorPane rootPane;
    public MenuBar menuBar;
    public HBox toolBar;

    public ToolPane[] tools;
    public About about;

    public Group content;
    public ScrollPane scroller;

    public void init(Stage parent){
        parentStage = parent;
        about = new About(parentStage);

        content = new Group();
        new ZoomPane(scroller, content);

        ToolButton button = new ToolButton("Test 1", Assets.logo);
        content.getChildren().add(button);
        button.setTranslateX(200);
        button.setTranslateY(200);
        button = new ToolButton("Test Center", Assets.logo);
        content.getChildren().add(button);
        button = new ToolButton("Test 2", Assets.logo);
        content.getChildren().add(button);
        button.setTranslateX(-300);
        button.setTranslateY(-300);

        initToolsPanels();
        initToolBar();

        rootPane.setOnMouseClicked(event -> closeAllToolPanes());
    }

    /** UI generation */
    private void initToolsPanels(){
        tools = new ToolPane[] {
                new ToolPane("Точки входа"),
                new ToolPane("Действия"),
                new ToolPane("Циклы"),
                new ToolPane("Логика")
        };
        for(Blocks.Desc block: Blocks.getInstance().all.values()){
            tools[block.category].add(block.tool);
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

    /** Events processing */
    public void closeAllToolPanes(){ toggleToolPane(-1); }
    public void toggleToolPane(int id){
        for(int c = 0; c < tools.length; c++)
            if(c == id) tools[c].toggle();
            else tools[c].close();
    }
    
    public void showAboutWindow() {
        about.show();
    }

    public void exit() {
        Platform.exit();
    }
}
