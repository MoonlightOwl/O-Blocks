package moonlightowl.openblocks.structure;

import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;

/**
 * OpenBlocks.Wire
 * Created by MoonlightOwl on 11/5/15.
 * ===
 * It's a line, connecting two joints
 */

public class Wire extends CubicCurve {
    public static double STRENGTH = 100;

    private Joint a, b;

    public Wire(){
        setStroke(Color.BLACK);
        setStrokeWidth(3);
        setFill(Color.RED);
    }

    public boolean link(Joint x) {
        if(x != a && x != b) {
            if(a == null) { a = x; return true; }
            else if(b == null) { b = x; return true; }
        }
        return false;
    }
    public boolean unlink(Joint x) {
        if(a == x) { a = null; return true; }
        else if(b == x) { b = null; return true; }
        return false;
    }

    public void reposition(){ reposition(0, 0); }
    public void reposition(double defX, double defY){
        //if(a != null && b != null) setVisible(true);
        //else setVisible(false);

        setStart(0, 0, 0, 0);
        double x = 300;
        setEnd(x, 0, x, 0);

        /*if(a != null)
            setStart(a.getAbsX(), a.getAbsY(),
                    a.getAbsX() + a.getNormalX()*STRENGTH, a.getAbsY() + a.getNormalY()*STRENGTH);
        else setStart(defX, defY, defX, defY);
        if(b != null)
            setEnd(b.getAbsX(), b.getAbsY(),
                    b.getAbsX() + b.getNormalX()*STRENGTH, b.getAbsY() + b.getNormalY()*STRENGTH);
        else setEnd(defX, defY, defX, defY);*/
    }
    private void setStart(double x, double y, double cx, double cy){
        setStartX(x); setStartY(y);
        setControlX1(cx); setControlY1(cy);
    }
    private void setEnd(double x, double y, double cx, double cy){
        setEndX(x); setEndY(y);
        setControlX2(cx); setControlY2(cy);
    }
}
