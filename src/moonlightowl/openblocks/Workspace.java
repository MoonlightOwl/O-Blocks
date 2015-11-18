package moonlightowl.openblocks;

import javafx.scene.control.ScrollPane;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Joint;
import moonlightowl.openblocks.structure.Wire;
import moonlightowl.openblocks.ui.ZoomPane;

import java.util.LinkedList;

/**
 * OpenBlocks.Workspace
 * Created by MoonlightOwl on 10/25/15.
 * ===
 * Working area: blocks, joints and wires
 */

public class Workspace {
    private ZoomPane zoomPane;
    private LinkedList<Block> blocks;
    private LinkedList<Wire> wires;

    public Workspace(ScrollPane scroller) {
        zoomPane = new ZoomPane(scroller);
        blocks = new LinkedList<>();
        wires = new LinkedList<>();
    }

    /** Geometry */
    public double projectX(double screenX){ return zoomPane.projectX(screenX); }
    public double projectY(double screenY){ return zoomPane.projectY(screenY); }

    public void drag(double deltaX, double deltaY){
        zoomPane.drag(deltaX, deltaY);
    }

    /** Blocks / joints / wires magic */
    public void addBlock(Block block){
        zoomPane.add(block);
        blocks.add(block);
    }
    public void addWire(Wire wire){
        zoomPane.addToBottom(wire);
        wires.add(wire);
    }

    public void removeBlock(Block block){ removeBlock(block, true); }
    private void removeBlock(Block block, boolean clean){
        if(block != null) {
            for (Joint j : block.getJoints()) removeWire(j.getWire());
            zoomPane.remove(block);
            if(clean) blocks.remove(block);
        }
    }
    public void removeWire(Wire wire){ removeWire(wire, true); }
    private void removeWire(Wire wire, boolean clean){
        if(wire != null) {
            wire.getJoints().forEach(Joint::detachWire);
            zoomPane.remove(wire);
            if(clean) wires.remove(wire);
        }
    }

    public void clear(){
        // Detach
        for(Block b: blocks) removeBlock(b, false); blocks.clear();
        for(Wire w: wires) removeWire(w, false); wires.clear();
        // Clean up
        zoomPane.clear();
    }

    public LinkedList<Block> getBlocks(){ return new LinkedList<>(blocks); }
    public LinkedList<Wire> getWires(){ return new LinkedList<>(wires); }
}
