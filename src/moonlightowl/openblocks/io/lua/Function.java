package moonlightowl.openblocks.io.lua;

import java.util.ArrayList;

/**
 * OpenBlocks.Function
 * Created by MoonlightOwl on 11/22/15.
 * ===
 * Function contains a list of other operators and functions
 */

public class Function extends Operator {
    private String name;
    private ArrayList<Operator> children;

    public Function(String name){
        this.name = name;
        children = new ArrayList<>();
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
    public Operator last() {
        return children.get(children.size()-1);
    }

    public String toString(){
        String body = "";
        for(Operator child: children)
            body += child.toString();
        return body;
    }
}
