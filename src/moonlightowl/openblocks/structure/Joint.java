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

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * OpenBlocks.Joint
 * Created by MoonlightOwl on 11/5/15.
 * ===
 * Joint is an object, that belongs to one block, and links to another
 */

public class Joint extends ImageView {
    public final static int FROM = 0, TO = 1, YES = 2, NO = 3, VARIABLE = 4, LINK = 5, ERROR = 6;
    public final static Color[] colors = {
            new Color(0.49, 0.98, 0.05, 1.0), new Color(0.98, 0.27, 0.13, 1.0),
            new Color(0.77, 0.98, 0.05, 1.0), new Color(0.05, 0.98, 0.50, 1.0),
            new Color(0.05, 0.89, 0.98, 1.0), new Color(0.98, 0.89, 0.05, 1.0),
            Color.VIOLET, Color.WHITE, Color.DARKGRAY
    };
    private static EventHandler<? super MouseEvent> listener;

    // Relative node coordinates (in block)
    private double x, y;
    private Point2D normal;

    // Joint
    private ArrayList<Wire> wires;
    private Block owner;
    private int action, data, ID;
    private boolean multiwired = false;

    public Joint(Block owner, double x, double y, int action, int id) {
        this(owner, x, y, action, Data.NOTHING, id);
    }
    public Joint(Block owner, double x, double y, int action, int data, int id) {
        this.owner = owner;
        this.x = x; this.y = y;
        this.action = action; this.data = data;
        this.ID = id;

        wires = new ArrayList<>();

        // Calculate normal vector to "circle bounds" of parent block
        normal = new Point2D(x, y+Block.DEPTH).normalize();

        // Set image
        changeImage();
        setTranslateX(owner.getWidth()/2 + x - 8); setTranslateY(owner.getHeight()/2 + y - 8);

        // Set color tint to ImageView
        setClip(new Circle(8, 8, 7.9));

        ColorInput colorize = new ColorInput(0, 0,
                Assets.node.getWidth(), Assets.node.getHeight(),
                colors[action]);
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

    private void changeImage() {
        if(action == YES)
            setImage(Assets.nodePlus);
        else if(action == NO)
            setImage(Assets.nodeMinus);
        else if(isMultiwired())
            setImage(Assets.nodeMulti);
        else
            setImage(Assets.node);
    }

    /** Getters */
    public double getRelativeX() { return x; }
    public double getRelativeY() { return y; }
    public double getAbsX() { return owner.getCenterX() + x; }
    public double getAbsY() { return owner.getCenterY() + y; }
    public double getNormalX() { return normal.getX(); }
    public double getNormalY() { return normal.getY(); }
    public boolean isAttached() { return !wires.isEmpty(); }
    public boolean isMultiwired() { return multiwired; }
    public Wire[] getWires(){ return wires.toArray(new Wire[wires.size()]); }
    public int getActionType(){ return action; }
    public boolean isActionFrom() { return action == FROM || action == YES || action == NO; }
    public int getDataType(){ return data; }
    public int getJointID(){ return ID; }
    public Joint getLink(Wire wire){
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


    /** Setters */
    public Joint setMultiwired(boolean multi) {
        this.multiwired = multi; changeImage(); return this;
    }
    public boolean attachWire(Wire wire){
        if(!multiwired) detachAllWires();
        if(wire != null) {
            if (wire.link(this)) {
                wires.add(wire);
                wire.reposition();
                return true;
            } else return false;
        }
        return true;
    }
    public boolean detachWire(Wire wire){
        if(wire != null){
            wire.unlink(this);
            wires.remove(wire);
            return true;
        }
        else return false;
    }
    public void detachAllWires() {
        for(Wire wire: wires) wire.unlink(this);
        wires.clear();
    }
    public void update(){
        wires.forEach(Wire::reposition);
    }

    public static void setOnClickListenter(EventHandler<? super MouseEvent> handler){
        listener = handler;
    }
}
