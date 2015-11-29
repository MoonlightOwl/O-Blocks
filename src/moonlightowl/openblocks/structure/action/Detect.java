package moonlightowl.openblocks.structure.action;

import moonlightowl.openblocks.Assets;
import moonlightowl.openblocks.Blocks;
import moonlightowl.openblocks.io.lua.Action;
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
        addJoint(new Joint(this, -37, 0, Joint.TO, 0).setMultiwired(true));
        addJoint(new Joint(this, 37, -10, Joint.FROM, Data.BOOLEAN, 1));
        addJoint(new Joint(this, 37, 10, Joint.VARIABLE, Data.BOOLEAN, 2).setMultiwired(true));
        setIcon(Assets.blockIconsIndex.get("detect.png"));
        operator = new Action("robot.detect()");
    }
}
