package moonlightowl.openblocks.structure.action;

import moonlightowl.openblocks.Assets;
import moonlightowl.openblocks.Blocks;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Data;
import moonlightowl.openblocks.structure.Joint;

/**
 * OpenBlocks.Detect
 * Created by MoonlightOwl on 11/20/15.
 * ===
 * Is there any obstacles on the way?
 */

public class Detect extends Block {
    public Detect(){
        super(0, 0, Blocks.Id.DETECT);
        addJoint(new Joint(this, -37, 0, Joint.TO, 0));
        addJoint(new Joint(this, 37, 0, Joint.FROM, Data.BOOLEAN, 1));
        setIcon(Assets.blockIcons[13]);
        code = "robot.detect()";
    }
}
