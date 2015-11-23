package moonlightowl.openblocks.io.lua;

/**
 * OpenBlocks.Variable
 * Created by MoonlightOwl on 11/23/15.
 * ===
 * Data storage
 */

public class Variable extends Operator {
    private String name;
    private Operator initializer;

    public Variable(String name, Operator initializer) {
        setName(name);
        setInitializer(initializer);
    }

    public Variable setName(String name) { this.name = name; return this; }
    public Variable setInitializer(Operator initializer) { this.initializer = initializer; return this; }
    public String getName() { return name; }
    public Operator getInitializer() { return initializer; }

    public String toString() {
        return "local " + name + " = " + initializer.toString();
    }
}
