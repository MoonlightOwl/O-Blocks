package moonlightowl.openblocks;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import moonlightowl.openblocks.ui.About;
import moonlightowl.openblocks.ui.ToolButton;
import moonlightowl.openblocks.ui.ToolPane;

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

    public void init(Stage parent){
        parentStage = parent;
        about = new About(parentStage);

        initToolsPanels();
        initToolBar();
    }

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
    public void yeah(ActionEvent actionEvent) {
        System.out.println("Yeah! Don't stop touching!");
    }
    public void toggleToolPane(int id){
        for(int c = 0; c < tools.length; c++)
            if(c == id) tools[c].toggle();
            else tools[c].close();
    }
    
    public void showAboutWindow(ActionEvent actionEvent) {
        about.show();
    }

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }
}
