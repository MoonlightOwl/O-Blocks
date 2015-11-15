package moonlightowl.openblocks.structure.action;

import moonlightowl.openblocks.Assets;
import moonlightowl.openblocks.Blocks;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Joint;

/**
 * OpenBlocks.Back
 * Created by MoonlightOwl on 11/8/15.
 * ===
 * Tell your robot not to stand still
 */

public class Back extends Block {
    public Back() {
        super(0, 0, Blocks.Id.BACK);
        addJoint(new Joint(this, -37, 0, Joint.TO, 0));
        addJoint(new Joint(this, 37, 0, Joint.FROM, 1));
        setIcon(Assets.blockIcons[1]);
        code = "robot.back()";
    }
}
