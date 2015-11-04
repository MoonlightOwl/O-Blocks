package moonlightowl.openblocks;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
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
        robotsTools.add(new ToolButton("Начало", new Image("/images/tools/robot/start.png")));
        robotsTools.add(new ToolButton("Конец", new Image("/images/tools/robot/end.png")));
        logicTools = new ToolPane("Логика");
        logicTools.add(new ToolButton("Если .. иначе", new Image("/images/tools/logic/if.png")));
        logicTools.add(new ToolButton("Меньше", new Image("/images/tools/logic/less.png")));
        logicTools.add(new ToolButton("Больше", new Image("/images/tools/logic/greater.png")));
        logicTools.add(new ToolButton("Равно", new Image("/images/tools/logic/equal.png")));
        logicTools.add(new ToolButton("Меньше или равно", new Image("/images/tools/logic/less_or_equal.png")));
        logicTools.add(new ToolButton("Больше или равно", new Image("/images/tools/logic/greater_or_equal.png")));
        logicTools.add(new ToolButton("Не равно", new Image("/images/tools/logic/not_equal.png")));
        logicTools.add(new ToolButton("Не", new Image("/images/tools/logic/not.png")));
        cycleTools = new ToolPane("Циклы");
        cycleTools.add(new ToolButton("Повторять пока", new Image("/images/tools/cycle/while.png")));
        cycleTools.add(new ToolButton("Повторять N раз", new Image("/images/tools/cycle/for.png")));
        cycleTools.add(new ToolButton("Вечный цикл", new Image("/images/tools/cycle/loop.png")));
        actionTools = new ToolPane("Действия");
        actionTools.add(new ToolButton("Двигаться", new Image("/images/tools/action/move.png")));
        actionTools.add(new ToolButton("Копать", new Image("/images/tools/action/dig.png")));
        actionTools.add(new ToolButton("Строить", new Image("/images/tools/action/build.png")));
        actionTools.add(new ToolButton("Быбрать слот", new Image("/images/tools/action/select_slot.png")));
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
