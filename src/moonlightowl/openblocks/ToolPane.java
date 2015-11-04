package moonlightowl.openblocks;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.WritableDoubleValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
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
    private TilePane panel;

    private int width = 400, actualWidth = width + 16;
    private boolean open = false;

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

        generatePanel();
    }
    private void generatePanel(){
        panel = new TilePane();
        panel.setPrefWidth(width);
        panel.setHgap(10); panel.setVgap(10);

        ScrollPane scrollPane = new ScrollPane(panel);
        scrollPane.setPadding(new Insets(10, 6, 10, 10));
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToWidth(true);
        setCenter(scrollPane);

        AnchorPane.setTopAnchor(this, 80.0);
        AnchorPane.setBottomAnchor(this, 150.0);

        WritableDoubleValue leftAnchor = new WritableDoubleValue() {
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
                if (value == null) set(0.0);
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
                new KeyFrame(Duration.ZERO,     new KeyValue(leftAnchor, 0.0)),
                new KeyFrame(new Duration(360), new KeyValue(leftAnchor, 1.05)),
                new KeyFrame(new Duration(400), new KeyValue(leftAnchor, 1.0))
        );
    }

    // Inner methods
    private void setPosition(double percent){
        AnchorPane.setLeftAnchor(this, -actualWidth + (actualWidth+10.0)*percent);
    }

    // Public interface
    public void add(Button button){
        panel.getChildren().add(button);
    }
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
