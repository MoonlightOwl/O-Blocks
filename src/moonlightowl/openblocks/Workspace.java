package moonlightowl.openblocks;

import javafx.scene.control.ScrollPane;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Joint;
import moonlightowl.openblocks.structure.Wire;
import moonlightowl.openblocks.ui.Selection;
import moonlightowl.openblocks.ui.ZoomPane;

import java.util.LinkedList;
import java.util.stream.Collectors;

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
    private Selection selection;
    private LinkedList<Block> selectedBlocks;
    private LinkedList<Wire> selectedWires;

    public Workspace(ScrollPane scroller) {
        zoomPane = new ZoomPane(scroller);
        blocks = new LinkedList<>();
        wires = new LinkedList<>();

        selectedBlocks = new LinkedList<>();
        selectedWires = new LinkedList<>();
        selection = new Selection();
        selection.setOnMoved((dx, dy) -> {
            if(isSelectionVisible())
                for(Block block: selectedBlocks)
                    block.setPosition(block.getX() + dx, block.getY() + dy);
            return null;
        });
        selection.setOnChanged(() -> {
            refreshSelection(); return null;
        });
        zoomPane.addTo(0, selection);
    }

    /** Geometry */
    public double projectX(double screenX){ return zoomPane.projectX(screenX); }
    public double projectY(double screenY){ return zoomPane.projectY(screenY); }
    public LinkedList<Block> getBlocks(){ return new LinkedList<>(blocks); }
    public LinkedList<Wire> getWires(){ return new LinkedList<>(wires); }

    public boolean isSelectionVisible() { return selection.isVisible(); }
    public double getSelectionX1() { return selection.getTranslateX(); }
    public double getSelectionY1() { return selection.getTranslateY(); }
    public double getSelectionX2() { return selection.getTranslateX() + selection.getWidth(); }
    public double getSelectionY2() { return selection.getTranslateY() + selection.getHeight(); }

    /** Workspace actions */
    public void drag(double deltaX, double deltaY){
        zoomPane.drag(deltaX, deltaY);
    }

    public void setSelectionVisible(boolean visible) { selection.setVisible(visible); }
    public void setSelectionX1(double x) { selection.setTranslateX(x); }
    public void setSelectionY1(double y) { selection.setTranslateY(y); }
    public void setSelectionX2(double x) { selection.setWidth(x - selection.getTranslateX()); }
    public void setSelectionY2(double y) { selection.setHeight(y - selection.getTranslateY()); }

    /** Blocks / joints / wires magic */
    public void addBlock(Block block){
        zoomPane.add(block);
        blocks.add(block);
    }
    public void addWire(Wire wire){
        zoomPane.addTo(1, wire);
        wires.add(wire);
    }

    public void removeBlock(Block block){ removeBlock(block, true); }
    private void removeBlock(Block block, boolean clean){
        if(block != null) {
            for (Joint j : block.getJoints())
                for(Wire wire: j.getWires())
                    removeWire(wire);
            zoomPane.remove(block);
            if(clean) blocks.remove(block);
        }
    }

    // Remove wire from list. If not 'clean' - then just detach from blocks
    public void removeWire(Wire wire){ removeWire(wire, true); }
    private void removeWire(Wire wire, boolean clean){
        if(wire != null) {
            for(Joint joint: wire.getJoints())
                joint.detachWire(wire);
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
        // Add selection
        zoomPane.add(selection);
    }

    /** Selection magic */
    private void refreshSelection() {
        selectedBlocks = blocks.stream()
                .filter(block -> selection.contains(block.getCenterX(), block.getCenterY()))
                .collect(Collectors.toCollection(LinkedList::new));
        selectedWires = wires.stream()
                .filter(wire -> selection.contains(wire.getStartX(), wire.getStartY()) &&
                                selection.contains(wire.getEndX(), wire.getEndY()))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public void clearSelection() {
        System.out.println(selectedBlocks.size() + " " + selectedWires.size());
        selectedBlocks.forEach(this::removeBlock); selectedBlocks.clear();
        selectedWires.forEach(this::removeWire); selectedWires.clear();
        setSelectionVisible(false);
    }
}
