package moonlightowl.openblocks.ui;

import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * OpenBlocks.Progress
 * Created by MoonlightOwl on 11/26/15.
 * ===
 * We do not stay motionless
 */
public class Progress {
    private Stage owner;
    private Popup popup;
    private ProgressIndicator indicator;

    public Progress(Stage owner) {
        this.owner = owner;

        StackPane content = new StackPane();
        indicator = new ProgressIndicator(-1.0);
        indicator.setStyle(" -fx-progress-color: white;");
        Rectangle back = new Rectangle(80, 80);
        back.setFill(Color.color(0, 0, 0, 0.5));
        back.setArcHeight(10);
        back.setArcWidth(10);
        content.getChildren().addAll(back, indicator);

        popup = new Popup();
        popup.getContent().addAll(content);
        popup.centerOnScreen();
        popup.setAutoFix(true);
    }

    public void show(){ popup.show(owner); }
    public void hide(){ popup.hide(); }
}
