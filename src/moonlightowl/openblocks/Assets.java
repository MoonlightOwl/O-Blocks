package moonlightowl.openblocks;

import javafx.scene.image.Image;

import java.io.File;

/**
 * OpenBlocks.Assets
 * Created by MoonlightOwl on 11/4/15.
 * ===
 * All app resources here
 */

public class Assets {
    public static File imagesPath = new File("/images/");

    public static Image logo, node;
    public static Image[] toolBarIcon, toolIcons, blockBack, blockIcons;

    public static Image loadImage(String name){
        return new Image(new File(imagesPath, name).getPath());
    }

    public static void load() {
        logo = loadImage("logo.png");
        node = loadImage("blocks/node.png");
        toolBarIcon = new Image[]{
                loadImage("tools/wire.png"),
                loadImage("tools/robot.png"),
                loadImage("tools/action.png"),
                loadImage("tools/cycle.png"),
                loadImage("tools/logic.png"),
                loadImage("tools/trash.png")
        };
        toolIcons = new Image[]{
                loadImage("tools/robot/start.png"),
                loadImage("tools/robot/end.png"),
                loadImage("tools/action/move.png"),
                loadImage("tools/action/dig.png"),
                loadImage("tools/action/build.png"),
                loadImage("tools/action/select_slot.png"),
                loadImage("tools/cycle/while.png"),
                loadImage("tools/cycle/for.png"),
                loadImage("tools/cycle/loop.png"),
                loadImage("tools/logic/if.png"),
                loadImage("tools/logic/less.png"),
                loadImage("tools/logic/greater.png"),
                loadImage("tools/logic/equal.png"),
                loadImage("tools/logic/less_or_equal.png"),
                loadImage("tools/logic/greater_or_equal.png"),
                loadImage("tools/logic/not_equal.png"),
                loadImage("tools/logic/not.png"),
        };
        blockBack = new Image[]{
                loadImage("blocks/robot.png"),
                loadImage("blocks/action.png"),
                loadImage("blocks/cycle.png"),
                loadImage("blocks/logic.png"),
        };
        blockIcons = new Image[]{
                loadImage("blocks/icons/move.png")
        };
    }
}
