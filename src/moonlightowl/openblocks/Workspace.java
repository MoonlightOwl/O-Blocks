package moonlightowl.openblocks;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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

    public ToolPane robotsTools, logicTools, cycleTools, actionTools;
    public About about;

    public void init(Stage parent){
        parentStage = parent;
        about = new About(parentStage);

        robotsTools = new ToolPane("Точки входа");
        logicTools = new ToolPane("Логика");
        cycleTools = new ToolPane("Циклы");
        actionTools = new ToolPane("Действия");
        rootPane.getChildren().addAll(robotsTools, logicTools, cycleTools, actionTools);
    }

    /** Events processing */
    public void yeah(ActionEvent actionEvent) {
        System.out.println("Yeah! Don't stop touching!");
    }
    public void toggleRobotTools(ActionEvent actionEvent) {
        robotsTools.toggle();
        logicTools.close(); cycleTools.close(); actionTools.close();
    }
    public void toggleLogicTools(ActionEvent actionEvent) {
        logicTools.toggle();
        robotsTools.close(); cycleTools.close(); actionTools.close();
    }
    public void toggleCycleTools(ActionEvent actionEvent) {
        cycleTools.toggle();
        robotsTools.close(); logicTools.close(); actionTools.close();
    }
    public void toggleActionTools(ActionEvent actionEvent) {
        actionTools.toggle();
        robotsTools.close(); logicTools.close(); cycleTools.close();
    }

    public void toggleToolbox(ActionEvent event){

    }
    public void showAboutWindow(ActionEvent actionEvent) {
        about.show();
    }

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }
}
