package moonlightowl.openblocks.structure.action;

import moonlightowl.openblocks.Assets;
import moonlightowl.openblocks.Blocks;
import moonlightowl.openblocks.io.lua.Action;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Data;
import moonlightowl.openblocks.structure.Joint;

/**
 * OpenBlocks.Input
 * Created by MoonlightOwl on 11/20/15.
 * ===
 * What do you want to say to me?
 */

public class Input extends Block {
    public Input(){
        super(0, 0, Blocks.Id.INPUT);
        addJoint(new Joint(this, -37, 0, Joint.TO, 0).setMultiwired(true));
        addJoint(new Joint(this, 37, -10, Joint.FROM, Data.STRING, 1));
        addJoint(new Joint(this, 37, 10, Joint.VARIABLE, Data.STRING, 2).setMultiwired(true));
        setIcon(Assets.blockIconsIndex.get("input.png"));
        operator = new Action("io.read()");
    }
}
