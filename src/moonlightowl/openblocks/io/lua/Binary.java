package moonlightowl.openblocks.io.lua;

/**
 * OpenBlocks.Binary
 * Created by MoonlightOwl on 11/27/15.
 * ===
 * Binary operator - returns result of processing two given values
 */

public class Binary extends Operator {
    private String modifier;
    private Operator exLeft, exRight;

    public Binary(String modifier, Operator exLeft, Operator exRight) {
        setModifier(modifier); setExpressions(exLeft, exRight);
    }

    public Binary setModifier(String modifier) { this.modifier = modifier; return this; }
    public Binary setExpressions(Operator exLeft, Operator exRight) {
        this.exLeft = exLeft; this.exRight = exRight; return this;
    }
    public String getModifier() { return modifier; }
    public Operator getLeftExpression() { return exLeft; }
    public Operator getRightExpression() { return exRight; }

    public String toString() {
        return exLeft.toString() + modifier + exRight.toString();
    }
}
