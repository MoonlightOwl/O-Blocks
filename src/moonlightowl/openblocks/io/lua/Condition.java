package moonlightowl.openblocks.io.lua;

import moonlightowl.openblocks.Settings;

/**
 * OpenBlocks.Condition
 * Created by MoonlightOwl on 11/22/15.
 * ===
 * if .. then .. else .. end
 */

public class Condition extends Compound {
    private Operator expression;
    private Function plus, minus;

    public Condition(Operator expression, Function plus, Function minus) {
        setExpression(expression);
        setPlusBranch(plus); setMinusBranch(minus);
    }

    public Condition setExpression(Operator expression) { this.expression = expression; return this; }
    public Condition setPlusBranch(Function branch) { this.plus = branch; return this; }
    public Condition setMinusBranch(Function branch) { this.minus = branch; return this; }

    public Operator getExpression() { return expression; }
    public Function getPlusBranch() { return plus; }
    public Function getMinusBranch() { return minus; }

    public String toString() {
        if(isOneLiner()) {
            minus.setIndent(0); minus.setOneLiner(true);
            plus.setIndent(0); plus.setOneLiner(true);
            return getIndentString() + "if " + expression.toString() + " then " +
                    plus.toString() + " else " + minus.toString() + " end";
        }
        else {
            minus.setIndent(1); minus.setOneLiner(false); String sMinus = minus.toString();
            plus.setIndent(1); plus.setOneLiner(false); String sPlus = plus.toString();
            return  getIndentString() + "if " + expression.toString() + " then" + Settings.EOL +
                    getIndentString() + (sPlus.isEmpty() ? Settings.INDENT + "-- pass --" : sPlus) + Settings.EOL +
                    getIndentString() + "else" + Settings.EOL +
                    getIndentString() + (sMinus.isEmpty() ? Settings.INDENT + "-- pass --" : sMinus) + Settings.EOL +
                    getIndentString() + "end";
        }
    }
}
