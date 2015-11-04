package moonlightowl.openblocks;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.WritableDoubleValue;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * OpenBlocks.ToolPane
 * Created by MoonlightOwl on 11/3/15.
 * ===
 * Slide-in panel with tools buttons
 */

public class ToolPane extends BorderPane {
    private Text title;
    private HBox panel;

    private int width = 400;
    private boolean open = false;

    private WritableDoubleValue leftAnchor;
    private Timeline animation;

    public ToolPane(){ this(""); }
    public ToolPane(String header) {
        setId("toolbox");
        setPadding(new Insets(5, 0, 20, 0));

        title = new Text(header);
        title.setId("toolbox-title");
        StackPane titleBar = new StackPane(title);
        titleBar.setId("toolbox");
        setTop(titleBar);

        panel = new HBox();
        panel.setPrefWidth(width);
        setCenter(panel);

        AnchorPane.setTopAnchor(this, 80.0);
        AnchorPane.setBottomAnchor(this, 150.0);

        leftAnchor = new WritableDoubleValue() {
            private double value;

            @Override
            public double get() {
                return value;
            }

            @Override
            public void set(double value) {
                this.value = value;
                setPosition(value);
            }

            @Override
            public void setValue(Number value) {
                if(value == null) set(0.0);
                else set(value.doubleValue());
            }

            @Override
            public Number getValue() {
                return this.value;
            }
        };
        leftAnchor.set(0.0);

        animation = new Timeline();
        animation.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(leftAnchor, 0.0)),
                new KeyFrame(new Duration(450),
                        new KeyValue(leftAnchor, 1.05)),
                new KeyFrame(new Duration(500),
                        new KeyValue(leftAnchor, 1.0))
        );
    }

    // Inner methods
    private void setPosition(double percent){
        AnchorPane.setLeftAnchor(this, -width + (width+10.0)*percent);
    }

    // Public interface
    public void setTitle(String text){
        title.setText(text);
    }
    public void toggle() {
        open = !open;
        if(open) { animation.setRate(1.0); animation.play(); }
        else { animation.setRate(-1.0); animation.play(); }
    }
    public void close() {
        if(open) toggle();
    }
    public void open() {
        if(!open) toggle();
    }

    public String getTitle(){ return title.getText(); }
    public boolean isOpen(){ return open; }
}
