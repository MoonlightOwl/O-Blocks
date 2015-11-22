package moonlightowl.openblocks.io.lua;

import moonlightowl.openblocks.Settings;

/**
 * OpenBlocks.Action
 * Created by MoonlightOwl on 11/22/15.
 * ===
 * Simple action call. Requires nothing, returns nothing
 */

public class Action extends Operator {
    private String code;

    public Action(String code) { setCode(code); }

    public Action setCode(String code) { this.code = code; return this; }
    public String getCode() { return code; }

    public String toString(){
        return getCode() + Settings.EOL;
    }
}
