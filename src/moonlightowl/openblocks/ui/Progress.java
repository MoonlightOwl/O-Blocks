package moonlightowl.openblocks.ui;

import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
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

        Pane content = new Pane();
        content.setPrefWidth(200);
        content.setPrefHeight(200);
        indicator = new ProgressIndicator(-1.0);
        indicator.setStyle(" -fx-progress-color: white;");
        indicator.setTranslateX(60);
        indicator.setTranslateY(60);
        indicator.setPrefWidth(80);
        indicator.setPrefHeight(80);
        Rectangle back = new Rectangle(100, 100);
        back.setFill(Color.color(0, 0, 0, 0.5));
        back.setArcHeight(10);
        back.setArcWidth(10);
        back.setTranslateX(50);
        back.setTranslateY(50);
        content.getChildren().addAll(back, indicator);

        popup = new Popup();
        popup.getContent().addAll(content);
        popup.setAutoFix(true);
    }

    public void show(){ popup.show(owner); }
    public void hide(){ popup.hide(); }
}
