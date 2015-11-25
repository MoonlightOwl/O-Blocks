package moonlightowl.openblocks.io.lua;

import moonlightowl.openblocks.Settings;

/**
 * OpenBlocks.Compound
 * Created by MoonlightOwl on 11/26/15.
 * ===
 * Something more complicated, than just an operator
 */

public class Compound extends Operator {
    protected int indent;
    protected boolean oneLiner = true;

    public void setIndent(int indent) { this.indent = indent; }
    public void indent() { indent++; }
    public void unindent() { if(indent > 0) indent--; }
    public void setOneLiner(boolean oneLiner) { this.oneLiner = oneLiner; }

    public int getIndent() { return indent; }
    public String getIndentString() { return new String(new char[indent]).replace("\0", Settings.INDENT); }
    public boolean isOneLiner() { return oneLiner; }
}
