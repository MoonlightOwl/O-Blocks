package moonlightowl.openblocks.structure.robot;

import moonlightowl.openblocks.Blocks.Category;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Node;

/**
 * OpenBlocks.Start
 * Created by MoonlightOwl on 11/7/15.
 * ===
 * Robot.Start - entry point of any robotic program
 */

public class Start extends Block {
    public Start() {
        super(0, 0, Category.ROBOT);
        addNode(new Node(this, 0, 46, Node.FROM));
    }
}
