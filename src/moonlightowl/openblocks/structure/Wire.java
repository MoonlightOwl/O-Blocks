package moonlightowl.openblocks.structure;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.StrokeLineCap;

import java.util.LinkedList;

/**
 * OpenBlocks.Wire
 * Created by MoonlightOwl on 11/5/15.
 * ===
 * It's a line, connecting two joints
 */

public class Wire extends Group {
    public static double STRENGTH = 80;

    private CubicCurve line, data;
    private Joint a, b;
    private int dataType = Data.NOTHING;

    private static EventHandler<? super MouseEvent> listener;

    public Wire(){
        line = new CubicCurve();
        data = new CubicCurve();
        
        line.setStroke(Color.BLACK);
        line.setFill(null);
        line.setStrokeWidth(4);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10.0);
        dropShadow.setOffsetY(6.0);
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.5));
        line.setEffect(dropShadow);

        data.setStroke(Data.color[dataType]);
        data.setFill(null);
        data.setStrokeWidth(2);
        data.getStrokeDashArray().addAll(6.0, 20.0);
        data.setStrokeLineCap(StrokeLineCap.ROUND);

        // Add listener
        line.setOnMouseClicked(listener);
        data.setOnMouseClicked(listener);
        
        getChildren().addAll(line, data);
        data.setVisible(false);
    }

    private void setStart(double x, double y, double cx, double cy){
        line.setStartX(x); line.setStartY(y);
        line.setControlX1(cx); line.setControlY1(cy);
        data.setStartX(x); data.setStartY(y);
        data.setControlX1(cx); data.setControlY1(cy);
    }
    private void setEnd(double x, double y, double cx, double cy){
        line.setEndX(x); line.setEndY(y);
        line.setControlX2(cx); line.setControlY2(cy);
        data.setEndX(x); data.setEndY(y);
        data.setControlX2(cx); data.setControlY2(cy);
    }


    public LinkedList<Joint> getJoints(){
        LinkedList<Joint> list = new LinkedList<>();
        if(a != null) list.add(a);
        if(b != null) list.add(b);
        return list;
    }
    public int getDataType() { return dataType; }


    public boolean link(Joint x) {
        boolean success = false;
        if(x != a && x != b) {
            if(a == null) { a = x; success = true; }
            else if(b == null) { b = x; success = true; }
        }
        if(success)
            if(x.getDataType() != Data.NOTHING) { setDataType(x.getDataType()); }
        return success;
    }
    public boolean unlink(Joint x) {
        boolean success = false;
        if(a == x) { a = null; success = true; }
        else if(b == x) { b = null; success = true; }
        if(success)
            if(x.getDataType() != Data.NOTHING)
                if(getDataType() == Data.ERROR)
                    if(a != null) setDataType(a.getDataType());
                    else if(b != null) setDataType(b.getDataType());
                    else setDataType(Data.NOTHING);
                else setDataType(Data.NOTHING);
        return success;
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

    public static void setOnClickListenter(EventHandler<? super MouseEvent> handler){
        listener = handler;
    }
    public void setDataType(int type) {
        if(this.dataType != Data.NOTHING) {
            this.dataType = Data.ERROR;
        }
        else this.dataType = type;

        if(this.dataType != Data.NOTHING) {
            data.setStroke(Data.color[this.dataType]);
            data.setVisible(true);
        }
        else
            data.setVisible(false);
    }
}
