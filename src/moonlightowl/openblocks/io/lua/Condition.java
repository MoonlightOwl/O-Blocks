package moonlightowl.openblocks.io.lua;

import moonlightowl.openblocks.Settings;

/**
 * OpenBlocks.Condition
 * Created by MoonlightOwl on 11/22/15.
 * ===
 * if .. then .. else .. end
 */

public class Condition extends Operator {
    private Operator expression;
    private Function plus, minus;

    public Condition(Operator expression, Function plus, Function minus) {
        this.expression = expression;
        this.plus = plus; this.minus = minus;
    }

    public Condition setExpression(Operator expression) { this.expression = expression; return this; }
    public Condition setPlusBranch(Function branch) { this.plus = branch; return this; }
    public Condition setMinusBranch(Function branch) { this.minus = branch; return this; }

    public Operator getExpression() { return expression; }
    public Function getPlusBranch() { return plus; }
    public Function getMinusBranch() { return minus; }

    public String toString() {
        return "if " + expression.toString() + " then " + Settings.EOL +
                plus.toString() +
                "else" + Settings.EOL +
                minus.toString() +
                "end" + Settings.EOL;
    }
}
