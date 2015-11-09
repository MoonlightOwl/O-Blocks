package moonlightowl.openblocks.ui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import moonlightowl.openblocks.Settings;

public class ZoomPane {
    final public static double SIZE_FACTOR = 10;
    final public static double SCALE_DELTA = 1.1;

    private ScrollPane scroller;
    private Pane content;
    private double scale = 1.0;

    public ZoomPane(ScrollPane root){
        scroller = root;
        createZoomPane();
        scroller.setHvalue(0.5);
        scroller.setVvalue(0.5);
    }

    private Parent createZoomPane() {
        content = new Pane();
        content.setPrefWidth(Settings.WIDTH * SIZE_FACTOR);
        content.setPrefHeight(Settings.HEIGHT * SIZE_FACTOR);
        content.setId("debug");

        scroller.setContent(content);

        content.setOnScroll(event -> {
            event.consume();
            // Break if trying to zoom in, or no zoom at all
            if(event.getDeltaY() == 0 || (event.getDeltaY() > 0 && scale >= 1.0)) return;

            // Scale content
            double scaleFactor = (event.getDeltaY() > 0) ? SCALE_DELTA : 1 / SCALE_DELTA;
            content.setScaleX(content.getScaleX() * scaleFactor);
            content.setScaleY(content.getScaleY() * scaleFactor);
            scale *= scaleFactor;
        });

        // Panning via drag
        final ObjectProperty<Point2D> lastMouseCoordinates = new SimpleObjectProperty<>();
        content.setOnMousePressed(event -> {
            if(event.getButton() == MouseButton.MIDDLE)
                lastMouseCoordinates.set(new Point2D(event.getX(), event.getY()));
        });

        content.setOnMouseDragged(event -> {
            if(event.getButton() == MouseButton.MIDDLE)
                drag(event.getX() - lastMouseCoordinates.get().getX(),
                     event.getY() - lastMouseCoordinates.get().getY());
        });

        return scroller;
    }

    /** Public interface */
    public double projectX(double screenX){
        double scroll = (content.getWidth() - scroller.getWidth()) * scroller.getHvalue();
        double offset = (content.getWidth() - (content.getWidth() * scale)) / 2;
        return (scroll + screenX - offset) / scale;
    }
    public double projectY(double screenY){
        double scroll = (content.getHeight() - scroller.getHeight()) * scroller.getVvalue();
        double offset = (content.getHeight() - (content.getHeight() * scale)) / 2;
        return (scroll + screenY - offset) / scale;
    }
    public int getChildrenCount(){ return content.getChildren().size(); }


    public void drag(double deltaX, double deltaY){
        double deltaH = (deltaX * scale) / content.getLayoutBounds().getWidth();
        double desiredH = scroller.getHvalue() - deltaH;
        scroller.setHvalue(Math.max(0, Math.min(scroller.getHmax(), desiredH)));

        double deltaV = (deltaY * scale) / content.getLayoutBounds().getHeight();
        double desiredV = scroller.getVvalue() - deltaV;
        scroller.setVvalue(Math.max(0, Math.min(scroller.getVmax(), desiredV)));
    }

    public void add(Node node){
        content.getChildren().add(node);
    }
    public void remove(Node node) {
        content.getChildren().remove(node);
    }
    public void addToBottom(Node node) {
        content.getChildren().add(0, node);
    }

    public void setOnClickListener(EventHandler<? super MouseEvent> listener){
        content.setOnMouseClicked(listener);
    }
}
