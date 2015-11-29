package moonlightowl.openblocks.util;

import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;

/**
 * OpenBlocks.ImageGen
 * Created by MoonlightOwl on 11/29/15.
 * ===
 * Converts nodes to images
 */

public class ImageGen {
    public static SnapshotParameters TOOL_ICON, SCREENSHOT;

    public static void init() {
        TOOL_ICON = new SnapshotParameters();
        TOOL_ICON.setFill(Color.TRANSPARENT);
        TOOL_ICON.setTransform(new Scale(0.8, 0.8));
        SCREENSHOT = new SnapshotParameters();
        SCREENSHOT.setFill(Color.TRANSPARENT);
    }

    public static WritableImage render(Parent node, SnapshotParameters params) {
        return node.snapshot(params, null);
    }
}
