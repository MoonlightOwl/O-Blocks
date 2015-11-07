package moonlightowl.openblocks.structure;

import javafx.geometry.Point2D;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import moonlightowl.openblocks.Assets;

/**
 * OpenBlocks.Node
 * Created by MoonlightOwl on 11/5/15.
 * ===
 * Node is an object, that belongs to one block, and links to another
 */

public class Node extends ImageView {
    public final static int FROM = 0, TO = 1, BOOLEAN = 2, NUMBER = 3, STRING = 4, LINK = 5, ERROR = 6;
    public final static Color[] colors = {
            new Color(0.49, 0.98, 0.05, 1.0), new Color(0.98, 0.27, 0.13, 1),
            new Color(0.98, 0.89, 0.05, 1.0), new Color(0.05, 0.89, 0.98, 1.0),
            Color.VIOLET, Color.WHITE, Color.DARKGRAY
    };

    // Relative note coordinates (in block)
    public double x, y;
    public Point2D normal;

    // Node
    public Wire wire;
    public Block link, owner;

    public int type;

    public Node(Block owner, double x, double y, int type) {
        this.owner = owner;
        this.x = x; this.y = y;
        this.type = type;

        // Calculate normal vector to "circle bounds" of parent block
        normal = new Point2D(x, y).normalize();

        // Set image
        setImage(Assets.node);
        setTranslateX(owner.getWidth()/2 + x - 8); setTranslateY(owner.getHeight()/2 + y - 8);

        // Set color tint to ImageView
        setClip(new Circle(8, 8, 7.9));

        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(-1.0);
        Blend tint = new Blend(
                BlendMode.MULTIPLY,
                null,
                new ColorInput(0, 0,
                        Assets.node.getWidth(), Assets.node.getHeight(),
                        colors[type])
        );
        setEffect(tint);
    }

    public double getRelativeX() { return x; }
    public double getRelativeY() { return y; }
    public double getAbsX() { return owner.getCenterX() + x; }
    public double getAbsY() { return owner.getCenterY() + y; }
    public double getNormalX(){ return normal.getX(); }
    public double getNormalY(){ return normal.getY(); }
}
