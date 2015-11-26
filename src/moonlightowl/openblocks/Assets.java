package moonlightowl.openblocks;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * OpenBlocks.Assets
 * Created by MoonlightOwl on 11/4/15.
 * ===
 * All app resources here
 */

public class Assets {
    public static String imagesPath = "/images/";

    public static Image logo, node, nodePlus, nodeMinus;
    public static Image[] toolBarIcon, toolIcons, blockBack;
    public static ArrayList<Image> blockIcons;
    public static HashMap<String, Image> blockIconsIndex;

    public static Image loadImage(String name) {
        return new Image(Assets.class.getResource(imagesPath + name).toExternalForm());
    }
    public static void loadBlockIcon(String name) {
        Image image = new Image(Assets.class.getResource(imagesPath + "blocks/icons/" + name).toExternalForm());
        blockIcons.add(image);
        blockIconsIndex.put(name, image);
    }

    public static void load() {
        logo = loadImage("logo.png");
        node = loadImage("blocks/node.png");
        nodePlus = loadImage("blocks/node_plus.png");
        nodeMinus = loadImage("blocks/node_minus.png");
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
                loadImage("tools/action/dig_up.png"),
                loadImage("tools/action/dig_down.png"),
                loadImage("tools/action/build.png"),
                loadImage("tools/action/build_up.png"),
                loadImage("tools/action/build_down.png"),
                loadImage("tools/action/detect.png"),
                loadImage("tools/action/detect_up.png"),
                loadImage("tools/action/detect_down.png"),
                loadImage("tools/logic/if.png"),
                loadImage("tools/logic/not.png"),
                loadImage("tools/logic/equals.png"),
                loadImage("tools/action/select_slot.png"),
                loadImage("tools/cycle/while.png"),
                loadImage("tools/cycle/for.png"),
                loadImage("tools/cycle/loop.png"),
                loadImage("tools/logic/less.png"),
                loadImage("tools/logic/greater.png"),
                loadImage("tools/logic/less_or_equal.png"),
                loadImage("tools/logic/greater_or_equal.png"),
                loadImage("tools/logic/not_equal.png"),
        };
        blockBack = new Image[]{
                loadImage("blocks/robot.png"),
                loadImage("blocks/action.png"),
                loadImage("blocks/cycle.png"),
                loadImage("blocks/logic.png"),
        };
        
        blockIconsIndex = new HashMap<>();
        blockIcons = new ArrayList<>();
        loadBlockIcon("forward.png");
        loadBlockIcon("back.png");
        loadBlockIcon("up.png");
        loadBlockIcon("down.png");
        loadBlockIcon("left.png");
        loadBlockIcon("right.png");
        loadBlockIcon("around.png");
        loadBlockIcon("dig.png");
        loadBlockIcon("dig_up.png");
        loadBlockIcon("dig_down.png");
        loadBlockIcon("build.png");
        loadBlockIcon("build_up.png");
        loadBlockIcon("build_down.png");
        loadBlockIcon("detect.png");
        loadBlockIcon("detect_up.png");
        loadBlockIcon("detect_down.png");
        loadBlockIcon("if.png");
        loadBlockIcon("not.png");
        loadBlockIcon("equals.png");
    }
}
