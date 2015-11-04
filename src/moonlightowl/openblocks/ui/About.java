package moonlightowl.openblocks.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import moonlightowl.openblocks.Log;
import moonlightowl.openblocks.Settings;

import java.io.IOException;

/**
 * OpenBlocks.About
 * Created by MoonlightOwl on 10/25/15.
 */

public class About {
    private final Stage window;

    public About(Stage owner) {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initOwner(owner);
        window.setTitle("О программе");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/about.fxml"));
            Scene scene = new Scene(root, 500, 400);
            window.setScene(scene);
            window.setResizable(false);

            Label version = (Label) scene.lookup("#version");
            version.setText(Settings.VERSION);
        } catch (IOException e) {
            Log.error("Cannot load 'about.fxml' file", e);
        }
    }

    public void show(){
        window.show();
    }
}
