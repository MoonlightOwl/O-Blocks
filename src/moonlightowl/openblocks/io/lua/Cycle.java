package moonlightowl.openblocks.io.lua;

import moonlightowl.openblocks.Settings;

/**
 * OpenBlocks.Cycle
 * Created by MoonlightOwl on 11/22/15.
 * ===
 * Repeats something somehow
 */

public class Cycle extends Compound {
    private Operator head, expression;
    private Function body;

    public Cycle(Operator head, Operator expression, Function body) {
        setHead(head);
        setExpression(expression);
        setBody(body);
    }

    public Cycle setHead(Operator head) { this.head = head; return this; }
    public Cycle setExpression(Operator expression) { this.expression = expression; return this; }
    public Cycle setBody(Function branch) { this.body = branch; return this; }

    public Operator getHead() { return head; }
    public Operator getExpression() { return expression; }
    public Function getBody() { return body; }

    public String toString() {
        if(isOneLiner()) {
            body.setIndent(0); body.setOneLiner(true);
            return getIndentString() + head.toString() + " " + expression.toString() + " do " +
                    body.toString() + " end";
        }
        else {
            body.setIndent(1); body.setOneLiner(false); String sBody = body.toString();
            return  getIndentString() + head.toString() + " " + expression.toString() + " do" + Settings.EOL +
                    getIndentString() + (sBody.isEmpty() ? Settings.INDENT + "-- pass --" : sBody) + Settings.EOL +
                    getIndentString() + "end";
        }
    }
}
