package moonlightowl.openblocks.structure.value;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import moonlightowl.openblocks.Blocks;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Joint;

import java.util.Optional;

/**
 * OpenBlocks.Const
 * Created by MoonlightOwl on 12/20/15.
 * ===
 * Immutable value
 */

public class Const extends Block {
    private Label text;
    private String value;

    public Const() {
        super(0, 0, Blocks.Id.CONST);
        addJoint(new Joint(this, 40, 0, Joint.VARIABLE, 1).setMultiwired(true));

        text = new Label();
        text.setTextFill(Color.WHITE);
        text.setAlignment(Pos.CENTER);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setWrapText(true);
        text.setTranslateX(10);
        text.setTranslateY(9);
        text.setPrefWidth(getWidth()-40);
        text.setPrefHeight(getHeight()-20);
        getChildren().add(text);

        setValue("Demo text");
    }

    public void setValue(String value) {
        this.value = value;
        text.setText(value);
    }
    public String getValue() { return value; }

    public void fetchData() {
        TextInputDialog dialog = new TextInputDialog("0");
        dialog.setTitle("Константа");
        dialog.setHeaderText("Введите значение");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(this::setValue);
    }
}
