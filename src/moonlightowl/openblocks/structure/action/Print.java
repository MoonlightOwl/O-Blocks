package moonlightowl.openblocks.structure.action;

import moonlightowl.openblocks.Assets;
import moonlightowl.openblocks.Blocks;
import moonlightowl.openblocks.io.lua.Action;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Joint;

/**
 * OpenBlocks.Print
 * Created by MoonlightOwl on 11/20/15.
 * ===
 * What do you want me to say?
 */

public class Print extends Block {
    public Print(){
        super(0, 0, Blocks.Id.PRINT);
        addJoint(new Joint(this, -37, 0, Joint.TO, 0).setMultiwired(true));
        addJoint(new Joint(this, 37, 0, Joint.FROM, 1));
        setIcon(Assets.blockIconsIndex.get("print.png"));
        operator = new Action("print");
    }
}
