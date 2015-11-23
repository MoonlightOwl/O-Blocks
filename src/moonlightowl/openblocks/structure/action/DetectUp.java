package moonlightowl.openblocks.structure.action;

import moonlightowl.openblocks.Assets;
import moonlightowl.openblocks.Blocks;
import moonlightowl.openblocks.io.lua.Action;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Data;
import moonlightowl.openblocks.structure.Joint;

/**
 * OpenBlocks.DetectUp
 * Created by MoonlightOwl on 11/20/15.
 * ===
 * Is there any obstacles on the way?
 */

public class DetectUp extends Block {
    public DetectUp(){
        super(0, 0, Blocks.Id.DETECTUP);
        addJoint(new Joint(this, -37, 0, Joint.TO, 0));
        addJoint(new Joint(this, 37, 0, Joint.FROM, Data.BOOLEAN, 1));
        setIcon(Assets.blockIconsIndex.get("detect_up.png"));
        operator = new Action("robot.detectUp()");
    }
}
