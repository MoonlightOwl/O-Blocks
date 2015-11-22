package moonlightowl.openblocks.structure.action;

import moonlightowl.openblocks.Assets;
import moonlightowl.openblocks.Blocks;
import moonlightowl.openblocks.io.lua.Action;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Joint;

/**
 * OpenBlocks.Right
 * Created by MoonlightOwl on 11/8/15.
 * ===
 * Tell your robot not to stand still
 */

public class Right extends Block {
    public Right() {
        super(0, 0, Blocks.Id.RIGHT);
        addJoint(new Joint(this, -37, 0, Joint.TO, 0));
        addJoint(new Joint(this, 37, 0, Joint.FROM, 1));
        setIcon(Assets.blockIcons[5]);
        operator = new Action("robot.turnRight()");
    }
}
