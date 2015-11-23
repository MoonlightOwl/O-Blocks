package moonlightowl.openblocks.io.lua;

/**
 * OpenBlocks.Unary
 * Created by MoonlightOwl on 11/23/15.
 * ===
 * Unary operator - changes given value
 */

public class Unary extends Operator {
    private String modifier;
    private Operator expression;

    public Unary(String modifier, Operator expression) {
        setModifier(modifier); setExpression(expression);
    }

    public Unary setModifier(String modifier) { this.modifier = modifier; return this; }
    public Unary setExpression(Operator expression) { this.expression = expression; return this; }
    public String getModifier() { return modifier; }
    public Operator getExpression() { return expression; }

    public String toString() {
        return modifier + expression.toString();
    }
}
