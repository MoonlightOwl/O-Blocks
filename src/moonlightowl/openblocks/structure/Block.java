package moonlightowl.openblocks.structure;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import moonlightowl.openblocks.Assets;
import moonlightowl.openblocks.Blocks;
import moonlightowl.openblocks.io.lua.Operator;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

/**
 * OpenBlocks.Block
 * Created by MoonlightOwl on 11/5/15.
 * ===
 * Single block - basic programming entity
 */

public class Block extends Group {
    public static final int DEPTH = 2;

    private static EventHandler<? super MouseEvent> listener;

    private Blocks.Id blockId;
    private String ID;
    private double x, y;

    private ImageView back, icon;
    private ArrayList<Joint> joints;

    protected Operator operator;

    public Block(double x, double y, Blocks.Id blockId){
        this.x = x; this.y = y; this.blockId = blockId;

        ID = UUID.randomUUID().toString();

        back = new ImageView(Assets.blockBack[blockId.category.ordinal()]);
        getChildren().add(back);
        setTranslateX(x); setTranslateY(y);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10.0);
        dropShadow.setOffsetY(6.0);
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.5));
        back.setEffect(dropShadow);

        icon = new ImageView();
        icon.setSmooth(true);
        getChildren().add(icon);

        joints = new ArrayList<>();

        // Make block draggable by mouse
        final ObjectProperty<Point2D> lastMouseCoordinates = new SimpleObjectProperty<>();
        setOnMousePressed(event -> {
            lastMouseCoordinates.set(new Point2D(event.getX(), event.getY()));
            getScene().setCursor(Cursor.MOVE);
            event.consume();
        });
        setOnMouseReleased(event -> getScene().setCursor(Cursor.HAND));
        setOnMouseDragged(event -> {
            setX(getX() + event.getX() - lastMouseCoordinates.get().getX());
            setY(getY() + event.getY() - lastMouseCoordinates.get().getY());
            joints.forEach(Joint::update);
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
        setOnMouseClicked(listener);
    }

    public Blocks.Id getBlockId(){ return blockId; }
    public String getID() { return ID; }
    public double getWidth(){ return back.getImage().getWidth(); }
    public double getHeight(){ return back.getImage().getHeight(); }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getCenterX(){ return x + getWidth()/2; }
    public double getCenterY(){ return y + getHeight()/2 - DEPTH; }
    public ArrayList<Joint> getJoints(){ return new ArrayList<>(joints); }
    public Optional<Joint> getJoint(int id){
        return joints.stream().filter(joint -> joint.getJointID() == id).findAny();
    }
    public Operator getOperator() { return operator; }

    public Block setX(double x) { this.x = x; setTranslateX(x); return this; }
    public Block setY(double y) { this.y = y; setTranslateY(y); return this; }
    public Block setPosition(double x, double y) {
        setX(x); setY(y); return this;
    }
    public Block addJoint(Joint joint){
        joints.add(joint);
        getChildren().add(joint);
        return this;
    }
    public Block setIcon(Image icon) {
        this.icon.setImage(icon);
        this.icon.setTranslateX(getWidth()/2 - icon.getWidth()/2);
        this.icon.setTranslateY(getHeight()/2 - icon.getHeight()/2 - DEPTH);
        return this;
    }

    public static void setOnClickListenter(EventHandler<? super MouseEvent> handler){
        listener = handler;
    }
}
