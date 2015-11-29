package moonlightowl.openblocks.structure.logic;

import moonlightowl.openblocks.Assets;
import moonlightowl.openblocks.Blocks;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Data;
import moonlightowl.openblocks.structure.Joint;

/**
 * OpenBlocks.Not
 * Created by MoonlightOwl on 11/23/15.
 * ===
 * Inverts boolean value
 */

public class Not extends Block {
    public Not() {
        super(0, 0, Blocks.Id.NOT);
        addJoint(new Joint(this, -39, -2, Joint.TO, Data.BOOLEAN, 0).setMultiwired(true));
        addJoint(new Joint(this, 39, -2, Joint.FROM, Data.BOOLEAN, 1));
        setIcon(Assets.blockIconsIndex.get("not.png"));
    }
}
