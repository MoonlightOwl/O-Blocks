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
    public About about;

    public void init(Stage parent){
        parentStage = parent;
        about = new About(parentStage);
    }

    /** Events processing */
    public void yeah(ActionEvent actionEvent) {
        System.out.println("Yeah! Don't stop touching!");
    }

    public void showAboutWindow(ActionEvent actionEvent) {
        about.show();
    }

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }
}
