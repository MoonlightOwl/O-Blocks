package moonlightowl.openblocks.structure;

import javafx.scene.paint.Color;

/**
 * OpenBlocks.Data
 * Created by MoonlightOwl on 11/20/15.
 * ===
 * Possible types of value that can be transmitted by wires
 */

public class Data {
    public final static int NOTHING = 0, BOOLEAN = 1, NUMBER = 2, STRING = 3, LINK = 4, ERROR = 5;
    public final static Color[] color = {
            Color.BLACK, Color.ORANGE, Color.CYAN, Color.GREEN, Color.LIGHTGRAY, Color.RED
    };
}
