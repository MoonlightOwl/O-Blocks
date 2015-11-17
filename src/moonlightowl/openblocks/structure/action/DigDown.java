package moonlightowl.openblocks.structure.action;

import moonlightowl.openblocks.Assets;
import moonlightowl.openblocks.Blocks;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Joint;

/**
 * OpenBlocks.Forward
 * Created by MoonlightOwl on 11/8/15.
 * ===
 * Tell your robot to swing with pickaxe
 */

public class DigDown extends Block {
    public DigDown() {
        super(0, 0, Blocks.Id.DIGDOWN);
        addJoint(new Joint(this, -37, 0, Joint.TO, 0));
        addJoint(new Joint(this, 37, 0, Joint.FROM, 1));
        setIcon(Assets.blockIcons[9]);
        code = "robot.swingDown()";
    }
}
