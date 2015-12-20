package moonlightowl.openblocks.structure.value;

import moonlightowl.openblocks.Blocks;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Joint;

/**
 * OpenBlocks.Const
 * Created by MoonlightOwl on 12/20/15.
 * ===
 * Immutable value
 */

public class Const extends Block {
    public Const() {
        super(0, 0, Blocks.Id.CONST);
        addJoint(new Joint(this, 40, 0, Joint.VARIABLE, 1).setMultiwired(true));
    }
}
