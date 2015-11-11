package moonlightowl.openblocks.structure;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import moonlightowl.openblocks.Assets;

import java.util.LinkedList;

/**
 * OpenBlocks.Joint
 * Created by MoonlightOwl on 11/5/15.
 * ===
 * Joint is an object, that belongs to one block, and links to another
 */

public class Joint extends ImageView {
    public final static int FROM = 0, TO = 1, BOOLEAN = 2, NUMBER = 3, STRING = 4, LINK = 5, ERROR = 6;
    public final static Color[] colors = {
            new Color(0.49, 0.98, 0.05, 1.0), new Color(0.98, 0.27, 0.13, 1),
            new Color(0.98, 0.89, 0.05, 1.0), new Color(0.05, 0.89, 0.98, 1.0),
            Color.VIOLET, Color.WHITE, Color.DARKGRAY
    };
    private static EventHandler<? super MouseEvent> listener;

    // Relative node coordinates (in block)
    private double x, y;
    private Point2D normal;

    // Joint
    private Wire wire;
    private Block owner;
    private int type, ID;

    public Joint(Block owner, double x, double y, int type, int id) {
        this.owner = owner;
        this.x = x; this.y = y;
        this.type = type; this.ID = id;

        // Calculate normal vector to "circle bounds" of parent block
        normal = new Point2D(x, y).normalize();

        // Set image
        setImage(Assets.node);
        setTranslateX(owner.getWidth()/2 + x - 8); setTranslateY(owner.getHeight()/2 + y - 8);

        // Set color tint to ImageView
        setClip(new Circle(8, 8, 7.9));

        ColorInput colorize = new ColorInput(0, 0,
                Assets.node.getWidth(), Assets.node.getHeight(),
                colors[type]);
        Bloom bloom = new Bloom();
        bloom.setThreshold(0.1);

        Blend tint = new Blend(BlendMode.MULTIPLY, null, colorize);
        Blend higlight = new Blend(BlendMode.MULTIPLY, bloom, colorize);
        setEffect(tint);

        // Set hover effect
        setOnMouseEntered(event -> setEffect(higlight));
        setOnMouseExited(event -> setEffect(tint));

        // Add listener
        setOnMouseClicked(listener);
    }

    public double getRelativeX() { return x; }
    public double getRelativeY() { return y; }
    public double getAbsX() { return owner.getCenterX() + x; }
    public double getAbsY() { return owner.getCenterY() + y; }
    public double getNormalX(){ return normal.getX(); }
    public double getNormalY(){ return normal.getY(); }
    public boolean isAttached(){ return wire != null; }
    public Wire getWire(){ return wire; }
    public int getType(){ return type; }
    public int getJointID(){ return ID; }
    public Joint getLink(){
        if(wire != null){
            LinkedList<Joint> joints = wire.getJoints();
            if(joints.size() > 1){
                if(joints.get(0) != this) return joints.get(0);
                else return joints.get(1);
            }
        }
        return null;
    }
    public Block getOwner(){ return owner; }

    public boolean attachWire(Wire wire){
        detachWire();
        if(wire != null) {
            if (wire.link(this)) {
                this.wire = wire;
                wire.reposition();
                return true;
            } else return false;
        }
        return true;
    }
    public boolean detachWire(){
        if(wire != null){
            wire.unlink(this);
            wire = null;
            return true;
        }
        else return false;
    }
    public void update(){
        if(wire != null) wire.reposition();
    }

    public static void setOnClickListenter(EventHandler<? super MouseEvent> handler){
        listener = handler;
    }
}
