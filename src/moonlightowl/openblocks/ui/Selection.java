package moonlightowl.openblocks.ui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * OpenBlocks.Selection
 * Created by MoonlightOwl on 11/27/15.
 * ===
 * That blue thing
 */

public class Selection extends Group {
    private Rectangle zone;
    private BiFunction<Double, Double, Void> onMoved;
    private Supplier<Void> onChanged;

    public Selection() {
        zone = new Rectangle(0, 0, 0, 0);
        zone.setStroke(Color.web("22597dff"));
        zone.setFill(Color.web("1e61754f"));
        zone.setStrokeWidth(2);
        zone.getStrokeDashArray().addAll(1.0, 4.0);

        Corner a = new Corner(dx -> { setTranslateX(getTranslateX() + dx); setWidth(getWidth() - dx); },
                       dy -> { setTranslateY(getTranslateY() + dy); setHeight(getHeight() - dy); });
        Corner b = new Corner(dx -> setWidth(getWidth() + dx),
                       dy -> { setTranslateY(getTranslateY() + dy); setHeight(getHeight() - dy); });
        b.translateXProperty().bind(zone.widthProperty());
        Corner c = new Corner(dx -> setWidth(getWidth() + dx),
                       dy -> setHeight(getHeight() + dy));
        c.translateXProperty().bind(zone.widthProperty());
        c.translateYProperty().bind(zone.heightProperty());
        Corner d = new Corner(dx -> { setTranslateX(getTranslateX() + dx); setWidth(getWidth() - dx); },
                       dy -> setHeight(getHeight() + dy));
        d.translateYProperty().bind(zone.heightProperty());

        getChildren().addAll(zone, a, b, c ,d);
        setVisible(false);

        // Make selection draggable by mouse
        final ObjectProperty<Point2D> lastMouseCoordinates = new SimpleObjectProperty<>();
        zone.setOnMousePressed(event -> {
            getScene().setCursor(Cursor.MOVE);
            lastMouseCoordinates.set(new Point2D(event.getX(), event.getY()));
            event.consume();
        });
        zone.setOnMouseReleased(event -> getScene().setCursor(Cursor.DEFAULT));
        zone.setOnMouseDragged(event -> {
            double dx = event.getX() - lastMouseCoordinates.get().getX(),
                   dy = event.getY() - lastMouseCoordinates.get().getY();
            setTranslateX(getTranslateX() + dx);
            setTranslateY(getTranslateY() + dy);
            if(onMoved != null) onMoved.apply(dx, dy);
            event.consume();
        });
    }

    public double getWidth() { return zone.getWidth(); }
    public double getHeight() { return zone.getHeight(); }
    public boolean contains(double x, double y) {
        return zone.contains(x - getTranslateX(), y - getTranslateY());
    }

    public void setWidth(double width) {
        zone.setWidth(width); onChanged.get();
    }
    public void setHeight(double height) {
        zone.setHeight(height); onChanged.get();
    }
    public void setOnMoved(BiFunction<Double, Double, Void> callback) {
        this.onMoved = callback;
    }
    public void setOnChanged(Supplier<Void> callback) {
        this.onChanged = callback;
    }

    private class Corner extends Circle {
        public Corner(Consumer<Double> setX, Consumer<Double> setY) {
            super(4);
            setStroke(Color.web("22597dff"));
            setFill(Color.WHITE);

            // Make corner draggable by mouse
            final ObjectProperty<Point2D> lastMouseCoordinates = new SimpleObjectProperty<>();
            setOnMousePressed(event -> {
                lastMouseCoordinates.set(new Point2D(event.getX(), event.getY()));
                getScene().setCursor(Cursor.MOVE);
                event.consume();
            });
            setOnMouseReleased(event -> getScene().setCursor(Cursor.HAND));
            setOnMouseDragged(event -> {
                setX.accept(event.getX() - lastMouseCoordinates.get().getX());
                setY.accept(event.getY() - lastMouseCoordinates.get().getY());
                event.consume();
            });
            setOnMouseEntered(event -> {
                if (!event.isPrimaryButtonDown()) {
                    getScene().setCursor(Cursor.HAND);
                }
            });
            setOnMouseExited(event -> {
                if (!event.isPrimaryButtonDown()) {
                    getScene().setCursor(Cursor.DEFAULT);
                }
            });
        }
    }
}
