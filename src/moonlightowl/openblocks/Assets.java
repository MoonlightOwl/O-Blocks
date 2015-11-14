package moonlightowl.openblocks;

import javafx.scene.image.Image;

/**
 * OpenBlocks.Assets
 * Created by MoonlightOwl on 11/4/15.
 * ===
 * All app resources here
 */

public class Assets {
    public static String imagesPath = "/images/";

    public static Image logo, node;
    public static Image[] toolBarIcon, toolIcons, blockBack, blockIcons;

    public static Image loadImage(String name){
        return new Image(Assets.class.getResource(imagesPath + name).toExternalForm());
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
                loadImage("tools/action/forward.png"),
                loadImage("tools/action/back.png"),
                loadImage("tools/action/up.png"),
                loadImage("tools/action/down.png"),
                loadImage("tools/action/left.png"),
                loadImage("tools/action/right.png"),
                loadImage("tools/action/around.png"),
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
                loadImage("blocks/icons/forward.png"),
                loadImage("blocks/icons/back.png"),
                loadImage("blocks/icons/up.png"),
                loadImage("blocks/icons/down.png"),
                loadImage("blocks/icons/left.png"),
                loadImage("blocks/icons/right.png"),
                loadImage("blocks/icons/around.png"),
        };
    }
}
