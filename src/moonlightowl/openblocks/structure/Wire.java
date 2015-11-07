package moonlightowl.openblocks.structure;

import javafx.scene.shape.CubicCurve;

/**
 * OpenBlocks.Wire
 * Created by MoonlightOwl on 11/5/15.
 * ===
 * It's a line, connecting two nodes
 */

public class Wire extends CubicCurve {
    public static double STRENGTH = 100;

    private Node a, b;

    public Wire(){
        setStrokeWidth(3);
    }

    public boolean link(Node x) {
        if(x != a && x != b) {
            if(a == null) { a = x; return true; }
            else if(b == null) { b = x; return true; }
        }
        return false;
    }
    public boolean unlink(Node x) {
        if(a == x) { a = null; return true; }
        else if(b == x) { b = null; return true; }
        return false;
    }

    public void reposition(){
        if(a != null && b != null) setVisible(true);
        else setVisible(false);

        if(a != null){
            setStartX(a.getAbsX());
            setStartY(a.getAbsY());
            setControlX1(a.getAbsX() + a.getNormalX()*STRENGTH);
            setControlY1(a.getAbsY() + a.getNormalY()*STRENGTH);
        }
        if(b != null){
            setEndX(b.getAbsX());
            setEndY(b.getAbsY());
            setControlX2(b.getAbsX() + b.getNormalX()*STRENGTH);
            setControlY2(b.getAbsY() + b.getNormalY()*STRENGTH);
        }
    }
}
