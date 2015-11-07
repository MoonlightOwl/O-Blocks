package moonlightowl.openblocks.structure;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import moonlightowl.openblocks.Assets;
import moonlightowl.openblocks.Blocks;

import java.util.ArrayList;

/**
 * OpenBlocks.Block
 * Created by MoonlightOwl on 11/5/15.
 * ===
 * Single block - basic programming entity
 */

public class Block extends Group {
    private Blocks.Category category;
    private double x, y;

    private ImageView back;
    private ArrayList<Node> nodes;

    public Block(double x, double y, Blocks.Category category){
        this.x = x; this.y = y; this.category = category;

        back = new ImageView(Assets.blockBack[category.ordinal()]);
        getChildren().add(back);
        setTranslateX(x); setTranslateY(y);

        nodes = new ArrayList<>();

        // Make block draggable by mouse
        final ObjectProperty<Point2D> lastMouseCoordinates = new SimpleObjectProperty<>();
        setOnMousePressed(event -> {
            lastMouseCoordinates.set(new Point2D(event.getX(), event.getY()));
            getScene().setCursor(Cursor.MOVE);
        });
        setOnMouseReleased(event -> getScene().setCursor(Cursor.HAND));
        setOnMouseDragged(event -> {
            setX(getX() + event.getX() - lastMouseCoordinates.get().getX());
            setY(getY() + event.getY() - lastMouseCoordinates.get().getY());
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

    public double getWidth(){ return back.getImage().getWidth(); }
    public double getHeight(){ return back.getImage().getHeight(); }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getCenterX(){ return x + getWidth()/2; }
    public double getCenterY(){ return y + getHeight()/2; }

    public Block setX(double x) { this.x = x; setTranslateX(x); return this; }
    public Block setY(double y) { this.y = y; setTranslateY(y); return this; }
    public Block setPosition(double x, double y) {
        setX(x); setY(y); return this;
    }
    public Block addNode(Node node){
        nodes.add(node);
        getChildren().add(node);
        return this;
    }
}
