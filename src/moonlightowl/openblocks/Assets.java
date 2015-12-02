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

    public static Image logo, node, nodePlus, nodeMinus, nodeMulti;
    public static Image[] toolBarIcon, blockBack;
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
        nodeMulti = loadImage("blocks/node_multi.png");

        toolBarIcon = new Image[]{
                loadImage("tools/wire.png"),
                loadImage("tools/robot.png"),
                loadImage("tools/action.png"),
                loadImage("tools/cycle.png"),
                loadImage("tools/logic.png"),
                loadImage("tools/trash.png")
        };

        blockBack = new Image[]{
                loadImage("blocks/robot_x.png"),
                loadImage("blocks/action_x.png"),
                loadImage("blocks/cycle_x.png"),
                loadImage("blocks/logic_x.png"),
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
        loadBlockIcon("input.png");
        loadBlockIcon("print.png");
        loadBlockIcon("for.png");
        loadBlockIcon("if.png");
        loadBlockIcon("not.png");
        loadBlockIcon("equals.png");
        loadBlockIcon("not_equals.png");
        loadBlockIcon("less.png");
        loadBlockIcon("less_or_equals.png");
        loadBlockIcon("greater.png");
        loadBlockIcon("greater_or_equals.png");
        loadBlockIcon("and.png");
        loadBlockIcon("or.png");
    }
}
