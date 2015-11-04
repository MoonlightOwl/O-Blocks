package moonlightowl.openblocks.ui;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * OpenBlocks.ToolButton
 * Created by MoonlightOwl on 11/4/15.
 * ===
 * Element of ToolPane
 */

public class ToolButton extends Button {
    public ToolButton(String name, Image graphics){
        setId("tool");
        setPrefWidth(120); setPrefHeight(120);
        setText(name);
        setGraphic(new ImageView(graphics));
        setContentDisplay(ContentDisplay.BOTTOM);
    }
}
