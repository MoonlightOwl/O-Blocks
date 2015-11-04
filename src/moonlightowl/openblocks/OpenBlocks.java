package moonlightowl.openblocks;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * OpenBlocks.OpenBlocks
 * Created by MoonlightOwl on 10/25/15.
 * ===
 * Entry point and main window
 */

public class OpenBlocks extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent root = loader.load();

        Assets.load();

        Workspace workspace = loader.getController();
        workspace.init(primaryStage);

        primaryStage.setTitle("OpenBlocks " + Settings.VERSION);
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
