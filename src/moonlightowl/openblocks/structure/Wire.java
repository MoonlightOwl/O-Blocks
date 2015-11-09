package moonlightowl.openblocks.structure;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;

import java.util.LinkedList;

/**
 * OpenBlocks.Wire
 * Created by MoonlightOwl on 11/5/15.
 * ===
 * It's a line, connecting two joints
 */

public class Wire extends CubicCurve {
    public static double STRENGTH = 80;

    private static EventHandler<? super MouseEvent> listener;

    private Joint a, b;

    public Wire(){
        setStroke(Color.BLACK);
        setStrokeWidth(4);
        setFill(null);

        // Add listener
        setOnMouseClicked(listener);
    }

    public LinkedList<Joint> getJoints(){
        LinkedList<Joint> list = new LinkedList<>();
        if(a != null) list.add(a);
        if(b != null) list.add(b);
        return list;
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
        if(a != null)
            setStart(a.getAbsX(), a.getAbsY(),
                    a.getAbsX() + a.getNormalX()*STRENGTH, a.getAbsY() + a.getNormalY()*STRENGTH);
        else setStart(defX, defY, defX, defY);
        if(b != null)
            setEnd(b.getAbsX(), b.getAbsY(),
                    b.getAbsX() + b.getNormalX()*STRENGTH, b.getAbsY() + b.getNormalY()*STRENGTH);
        else setEnd(defX, defY, defX, defY);
    }
    private void setStart(double x, double y, double cx, double cy){
        setStartX(x); setStartY(y);
        setControlX1(cx); setControlY1(cy);
    }
    private void setEnd(double x, double y, double cx, double cy){
        setEndX(x); setEndY(y);
        setControlX2(cx); setControlY2(cy);
    }

    public static void setOnClickListenter(EventHandler<? super MouseEvent> handler){
        listener = handler;
    }
}
