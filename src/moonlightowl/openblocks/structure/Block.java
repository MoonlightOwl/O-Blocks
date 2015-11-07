package moonlightowl.openblocks.structure;

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
