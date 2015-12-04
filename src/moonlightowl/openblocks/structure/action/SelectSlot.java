package moonlightowl.openblocks.structure.action;

import moonlightowl.openblocks.Assets;
import moonlightowl.openblocks.Blocks;
import moonlightowl.openblocks.io.lua.Action;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Joint;

/**
 * OpenBlocks.SelectSlot
 * Created by MoonlightOwl on 11/20/15.
 * ===
 * Select inventory slot by given number
 */

public class SelectSlot extends Block {
    public SelectSlot(){
        super(0, 0, Blocks.Id.SELECTSLOT);
        addJoint(new Joint(this, -37, 0, Joint.TO, 0).setMultiwired(true));
        addJoint(new Joint(this, 37, 0, Joint.FROM, 1));
        setIcon(Assets.blockIconsIndex.get("select_slot.png"));
        operator = new Action("robot.select");
    }
}
