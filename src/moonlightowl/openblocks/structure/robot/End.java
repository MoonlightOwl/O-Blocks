package moonlightowl.openblocks.structure.robot;

import moonlightowl.openblocks.Blocks;
import moonlightowl.openblocks.io.lua.Action;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Joint;

/**
 * OpenBlocks.End
 * Created by MoonlightOwl on 11/7/15.
 * ===
 * Block that finishes the program
 */

public class End extends Block {
    public End(){
        super(0, 0, Blocks.Id.END);
        addJoint(new Joint(this, 0, -50, Joint.TO, 0).setMultiwired(true));
        operator = new Action("os.exit()");
    }
}
