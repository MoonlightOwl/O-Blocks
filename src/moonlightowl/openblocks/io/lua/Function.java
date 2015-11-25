package moonlightowl.openblocks.io.lua;

import moonlightowl.openblocks.Settings;

import java.util.ArrayList;

/**
 * OpenBlocks.Function
 * Created by MoonlightOwl on 11/22/15.
 * ===
 * Function contains a list of other operators and functions
 */

public class Function extends Compound {
    private String name;
    private ArrayList<Operator> children;

    public Function(String name){
        this.name = name;
        children = new ArrayList<>();
        indent = 0;
        oneLiner = false;
    }

    public Function setName(String name) { this.name = name; return this; }
    public Function add(Operator operator) {
        children.add(operator); return this;
    }
    public Function clear() {
        children.clear(); return this;
    }

    public String getName() { return name; }
    public ArrayList<Operator> getChildren() { return children; }
    public int size() { return children.size(); }
    public Operator last() {
        return children.get(children.size()-1);
    }

    public String toString(){
        String body = "";
        if(oneLiner) {
            for (Operator child : children)
                if (!child.toString().isEmpty())
                    body += (body.isEmpty() ? getIndentString() : "") +
                            child.toString() + "; ";
        } else {
            for (Operator child : children)
                if (!child.toString().isEmpty())
                    body += (body.isEmpty() ? "" : Settings.EOL) +
                            getIndentString() +
                            child.toString();
        }
        return body;
    }
}
