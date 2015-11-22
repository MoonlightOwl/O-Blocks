package moonlightowl.openblocks.structure.robot;

import moonlightowl.openblocks.Blocks;
import moonlightowl.openblocks.io.lua.Action;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Joint;

/**
 * OpenBlocks.Start
 * Created by MoonlightOwl on 11/7/15.
 * ===
 * Robot.Start - entry point of any robotic program
 */

public class Start extends Block {
    public Start() {
        super(0, 0, Blocks.Id.START);
        addJoint(new Joint(this, 0, 46, Joint.FROM, 0));
        operator = new Action("local robot = require('robot')");
    }
}
