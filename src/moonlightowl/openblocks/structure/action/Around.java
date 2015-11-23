package moonlightowl.openblocks.structure.action;

import moonlightowl.openblocks.Assets;
import moonlightowl.openblocks.Blocks;
import moonlightowl.openblocks.io.lua.Action;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Joint;

/**
 * OpenBlocks.Around
 * Created by MoonlightOwl on 11/8/15.
 * ===
 * Tell your robot not to stand still
 */

public class Around extends Block {
    public Around() {
        super(0, 0, Blocks.Id.AROUND);
        addJoint(new Joint(this, -37, 0, Joint.TO, 0));
        addJoint(new Joint(this, 37, 0, Joint.FROM, 1));
        setIcon(Assets.blockIconsIndex.get("around.png"));
        operator = new Action("robot.turnAround()");
    }
}
