package moonlightowl.openblocks.structure.logic;

import moonlightowl.openblocks.Assets;
import moonlightowl.openblocks.Blocks;
import moonlightowl.openblocks.io.lua.Action;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Joint;

/**
 * OpenBlocks.${CLASS}
 * Created by MoonlightOwl on 11/19/15.
 * ===
 * Basic logical class
 */

public class If extends Block {
    public If() {
        super(0, 0, Blocks.Id.IF);
        addJoint(new Joint(this, -39, -2, Joint.TO, 0));
        addJoint(new Joint(this, 0, -42, Joint.NO, 2));
        addJoint(new Joint(this, 0, 39, Joint.YES, 3));
        setIcon(Assets.blockIcons[14]);
        operator = new Action("if true then end");
    }
}
