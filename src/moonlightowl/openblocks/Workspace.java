package moonlightowl.openblocks;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Joint;
import moonlightowl.openblocks.structure.Wire;
import moonlightowl.openblocks.ui.Selection;
import moonlightowl.openblocks.ui.ZoomPane;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * OpenBlocks.Workspace
 * Created by MoonlightOwl on 10/25/15.
 * ===
 * Working area: blocks, joints and wires
 */

public class Workspace {
    private ZoomPane zoomPane;
    private LinkedList<Block> blocks, selectedBlocks, bufferedBlocks;
    private LinkedList<Wire> wires, selectedWires, bufferedWires;
    private Selection selection;
    private boolean noRefresh = false;

    public Workspace(ScrollPane scroller) {
        zoomPane = new ZoomPane(scroller);
        blocks = new LinkedList<>();
        wires = new LinkedList<>();
        selectedBlocks = new LinkedList<>();
        selectedWires = new LinkedList<>();
        bufferedBlocks = new LinkedList<>();
        bufferedWires = new LinkedList<>();

        selection = new Selection();
        selection.setOnMoved((dx, dy) -> {
            if(isSelectionVisible())
                for(Block block: selectedBlocks)
                    block.setPosition(block.getX() + dx, block.getY() + dy);
            return null;
        });
        selection.setOnChanged(() -> {
            if(!noRefresh) refreshSelection(); return null;
        });
        zoomPane.addTo(0, selection);
    }

    /** Geometry */
    public double projectX(double screenX){ return zoomPane.projectX(screenX); }
    public double projectY(double screenY){ return zoomPane.projectY(screenY); }

    public LinkedList<Block> getBlocks(){ return new LinkedList<>(blocks); }
    public LinkedList<Wire> getWires(){ return new LinkedList<>(wires); }
    public WritableImage snapshot() {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Rectangle2D bounds = getBoundsOf(blocks);
        params.setViewport(bounds);
        return zoomPane.snapshot(params, null);
    }

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

    private Rectangle2D getBoundsOf(LinkedList<Block> blocks) {
        int padding = 10;
        Optional<Block> leftmost = blocks.stream()
                .min((a, b) -> Double.compare(a.getCenterX(), b.getCenterX())),
                rightmost = blocks.stream()
                        .max((a, b) -> Double.compare(a.getCenterX(), b.getCenterX())),
                topmost = blocks.stream()
                        .min((a, b) -> Double.compare(a.getCenterY(), b.getCenterY())),
                bottommost = blocks.stream()
                        .max((a, b) -> Double.compare(a.getCenterY(), b.getCenterY()));
        double x = leftmost.isPresent() ? leftmost.get().getX() : 0,
               y = topmost.isPresent() ? topmost.get().getY() : 0,
               width = rightmost.isPresent() ? rightmost.get().getX() + rightmost.get().getWidth() - x : 100,
               height = bottommost.isPresent() ? bottommost.get().getY() + bottommost.get().getHeight() - y : 100;
        return new Rectangle2D(x-padding, y-padding, width + padding*2, height + padding*2);
    }
    public void selectAll(LinkedList<Block> blocks) {
        if(blocks == null) blocks = this.blocks;
        Rectangle2D bounds = getBoundsOf(blocks);
        setSelectionX1(bounds.getMinX());
        setSelectionY1(bounds.getMinY());
        setSelectionX2(bounds.getMaxX());
        setSelectionY2(bounds.getMaxY());
        setSelectionVisible(true);
    }

    public void clearSelection() {
        selectedBlocks.forEach(this::removeBlock); selectedBlocks.clear();
        selectedWires.forEach(this::removeWire); selectedWires.clear();
        setSelectionVisible(false);
    }

    private class Elements {
        public LinkedList<Block> blocks = new LinkedList<>();
        public LinkedList<Wire> wires = new LinkedList<>();
    }
    private Elements deepCopyOf(LinkedList<Block> blocks, LinkedList<Wire> wires) {
        Elements elements = new Elements();
        HashMap<Block, Block> index = new HashMap<>();
        for(Block block: blocks) {
            Block clone = block.getBlockId().getInstance().setPosition(block.getX(), block.getY());
            index.put(block, clone);
        }
        elements.blocks.addAll(index.values());
        for(Wire wire: wires) {
            Wire clone = new Wire();
            for(Joint joint: wire.getJoints())
                index.get(joint.getOwner()).getJoint(joint.getJointID()).get().attachWire(clone);
            elements.wires.add(clone);
        }
        return elements;
    }
    public void copySelection() {
        Elements clone = deepCopyOf(selectedBlocks, selectedWires);
        bufferedBlocks = clone.blocks;
        bufferedWires = clone.wires;
    }
    public void cutSelection() {
        copySelection(); clearSelection();
    }
    public void pasteSelection() {
        // Clone all buffered blocks
        Elements clone = deepCopyOf(bufferedBlocks, bufferedWires);
        selectedBlocks = clone.blocks;
        selectedWires = clone.wires;
        // Add cloned elements to selection
        selectedBlocks.forEach(this::addBlock);
        selectedWires.forEach(this::addWire);
        // Calculate new selection rectangle
        noRefresh = true;
        selectAll(selectedBlocks);
        noRefresh = false;
    }
}
