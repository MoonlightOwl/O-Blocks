package moonlightowl.openblocks.structure.action;

import moonlightowl.openblocks.Assets;
import moonlightowl.openblocks.Blocks;
import moonlightowl.openblocks.io.lua.Action;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Joint;

/**
 * OpenBlocks.Forward
 * Created by MoonlightOwl on 11/8/15.
 * ===
 * Tell your robot to swing with pickaxe
 */

public class DigUp extends Block {
    public DigUp() {
        super(0, 0, Blocks.Id.DIGUP);
        addJoint(new Joint(this, -37, 0, Joint.TO, 0));
        addJoint(new Joint(this, 37, 0, Joint.FROM, 1));
        setIcon(Assets.blockIconsIndex.get("dig_up.png"));
        operator = new Action("robot.swingUp()");
    }
}
