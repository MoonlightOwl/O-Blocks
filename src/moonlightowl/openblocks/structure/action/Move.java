package moonlightowl.openblocks.structure.action;

import moonlightowl.openblocks.Assets;
import moonlightowl.openblocks.Blocks;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Joint;

/**
 * OpenBlocks.Move
 * Created by MoonlightOwl on 11/8/15.
 * ===
 * Tell your robot not to stand still
 */

public class Move extends Block {
    public Move() {
        super(0, 0, Blocks.Category.ACTION);
        addJoint(new Joint(this, -37, 0, Joint.TO));
        addJoint(new Joint(this, 37, 0, Joint.FROM));
        setIcon(Assets.blockIcons[0]);
    }
}
