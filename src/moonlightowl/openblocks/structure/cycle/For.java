package moonlightowl.openblocks.structure.cycle;

import moonlightowl.openblocks.Assets;
import moonlightowl.openblocks.Blocks;
import moonlightowl.openblocks.io.lua.Action;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Data;
import moonlightowl.openblocks.structure.Joint;

/**
 * OpenBlocks.For
 * Created by MoonlightOwl on 12/2/15.
 * ===
 * Over and over and over again
 */

public class For extends Block {
    public For(){
        super(0, 0, Blocks.Id.FOR);
        addJoint(new Joint(this, -37, -2, Joint.TO, 0).setMultiwired(true));
        addJoint(new Joint(this, 0, -40, Joint.NO, 2));
        addJoint(new Joint(this, 0, 37, Joint.YES, 3));
        addJoint(new Joint(this, 37, -2, Joint.VARIABLE, Data.NUMBER, 4).setMultiwired(true));
        setIcon(Assets.blockIconsIndex.get("for.png"));
        operator = new Action("for");
    }
}
