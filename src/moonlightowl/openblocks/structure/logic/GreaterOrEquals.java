package moonlightowl.openblocks.structure.logic;

import moonlightowl.openblocks.Assets;
import moonlightowl.openblocks.Blocks;
import moonlightowl.openblocks.io.lua.Binary;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Data;
import moonlightowl.openblocks.structure.Joint;

/**
 * OpenBlocks.GreaterOrEquals
 * Created by MoonlightOwl on 11/26/15.
 * ===
 * The greater good (non strict version)
 */

public class GreaterOrEquals extends Block {
    public GreaterOrEquals() {
        super(0, 0, Blocks.Id.GREATEROREQUALS);
        addJoint(new Joint(this, -39, -2, Joint.TO, Data.BOOLEAN, 0));
        addJoint(new Joint(this, 39, -2, Joint.TO, Data.BOOLEAN, 1));
        addJoint(new Joint(this, 0, -42, Joint.FROM, Data.BOOLEAN, 2));
        addJoint(new Joint(this, 0, 39, Joint.VARIABLE, Data.BOOLEAN, 3).setMultiwired(true));
        setIcon(Assets.blockIconsIndex.get("greater_or_equals.png"));
        operator = new Binary(" >= ", null, null);
    }
}
