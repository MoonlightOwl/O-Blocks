package moonlightowl.openblocks.io;

import moonlightowl.openblocks.Blocks;
import moonlightowl.openblocks.Settings;
import moonlightowl.openblocks.Workspace;
import moonlightowl.openblocks.io.lua.*;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Data;
import moonlightowl.openblocks.structure.Joint;
import moonlightowl.openblocks.structure.Wire;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Optional;

import static moonlightowl.openblocks.Blocks.Id.END;
import static moonlightowl.openblocks.Blocks.Id.START;

/**
 * OpenBlocks.Lua
 * Created by MoonlightOwl on 11/13/15.
 * ===
 * Core project export module - parses workspace, builds programs structure and translates to Lua
 */

public class Lua {
    private static HashMap<String, String> namespace = new HashMap<>();
    private static HashMap<String, String> gotospace = new HashMap<>();

    public static boolean export(Workspace workspace, OutputStream stream) throws IOException {
        NameGen.init();
        namespace.clear();
        gotospace.clear();
        // Create root structure for project
        Function program = new Function("main");
        // Let's get started - search for Start block
        for(Block block: workspace.getBlocks())
            if(block.getBlockId() == START) {
                new Tracer(block, program).run();
                break;
            }
        // Export
        stream.write(("-- [OcBlocks v" + Settings.VERSION + " generated code] --" + Settings.EOL).getBytes());
        stream.write((program.toString() + Settings.EOL).getBytes());
        stream.write("-- [The END] --".getBytes());
        return true;
    }

    private static class Tracer {
        private Block current;
        private Joint entry;
        private Function function;

        public Tracer(Block beginFrom, Function function){
            this.current = beginFrom;
            this.function = function;
        }
        public void run() throws IOException {
            while(current != null){
                // Create goto mark
                if(entry != null) {
                    if(entry.isMultiwired()) {
                        if(entry.getWires().length > 1) {
                            // Add new goto mark
                            if (gotospace.get(current.getID()) == null) {
                                String name = NameGen.getName();
                                // Cycles do not need goto marks
                                if(current.getBlockId().category != Blocks.Category.CYCLE)
                                    function.add(new Action("::" + name + "::"));
                                gotospace.put(current.getID(), name);
                            }
                            // Or break processing, and go to to existing implementation
                            else {
                                // Cycles do not need goto actions
                                if(current.getBlockId().category != Blocks.Category.CYCLE)
                                    function.add(new Action("goto " + gotospace.get(current.getID())));
                                break;
                            }
                        }
                    }
                }
                // Build structure
                String name;
                switch (current.getBlockId()) {
                    case IF:
                        Function plus = new Function("plus"), minus = new Function("minus");
                        // Build condition
                        Condition condition = new Condition(new Action(getLastValue("false")), plus, minus);
                        condition.setIndent(function.getIndent());
                        // Run condition branches
                        Block plusBlock = otherSideOf(current, Joint.YES);
                        if(plusBlock != null) new Tracer(plusBlock, plus).run();
                        Block minusBlock = otherSideOf(current, Joint.NO);
                        if(minusBlock != null) new Tracer(minusBlock, minus).run();
                        //
                        function.add(condition);
                        break;
                    case FOR:
                        // Get last runtime variable, or look for available variables among all wires
                        String cap = getLastValue(null);
                        if(cap == null) cap = seekVariables(current.getJoint(0).get());
                        cap = (cap == null ? "0" : "tonumber("+cap+")");
                        // Build cycle
                        Function body = new Function("body");
                        name = NameGen.getName();
                        Cycle cycle = new Cycle(current.getOperator(), new Action(name + " = 1, " + cap), body);
                        namespace.put(current.getID(), name);
                        // Run body branch
                        Block bodyBlock = otherSideOf(current, Joint.YES);
                        if(bodyBlock != null) new Tracer(bodyBlock, body).run();
                        //
                        function.add(cycle);
                        break;
                    case NOT:
                        name = NameGen.getName();
                        function.add(
                                new Variable(name, new Unary("not ", new Action(getLastValue("false")))));
                        namespace.put(current.getID(), name);
                        break;
                    case PRINT:
                        name = getLastValue(null);
                        if(name == null) name = seekVariables(current.getJoint(0).get());
                        if(name == null) name = "";
                        function.add(
                                new Action(current.getOperator().toString() + "(" + name + ")"));
                        break;
                    case SELECTSLOT:
                        name = getLastValue(null);
                        if(name == null) name = seekVariables(current.getJoint(0).get());
                        if(name == null) name = "1";
                        function.add(
                                new Action(current.getOperator().toString() + "(" + "tonumber("+name+")" + ")"));
                        break;
                    case EQUALS: case NOTEQUALS: case AND: case OR:
                    case LESS: case LESSOREQUALS: case GREATER: case GREATEROREQUALS:
                        Optional<Joint> a = current.getJoint(0), b = current.getJoint(1);
                        Block blockA = (a.isPresent() ? otherSideOf(a.get()) : null),
                              blockB = (b.isPresent() ? otherSideOf(b.get()) : null);
                        String varA = (blockA != null ? namespace.get(blockA.getID()) : null),
                               varB = (blockB != null ? namespace.get(blockB.getID()) : null);
                        Operator opA = new Action(varA != null ? varA : "false"),
                                 opB = new Action(varB != null ? varB : "false");
                        String nameForEquals = NameGen.getName();
                        function.add(new Variable(nameForEquals,
                                ((Binary)current.getOperator()).setExpressions(opA, opB)));
                        namespace.put(current.getID(), nameForEquals);
                        break;
                    default:
                        Joint from = jointOf(current, Joint.FROM);
                        if(from != null && from.getDataType() != Data.NOTHING) {
                            name = NameGen.getName();
                            function.add(new Variable(name, current.getOperator()));
                            namespace.put(current.getID(), name);
                        }
                        else function.add(current.getOperator());
                }
                // Finita la commedia
                if(current.getBlockId() == END) break;
                // Or move to the next block
                entry = otherSideJointOf(jointOf(current, Joint.FROM));
                current = (entry != null ? entry.getOwner() : null);
                // Finita again?
                if(current != null && current.getBlockId() == START) break;
            }
        }

        /**
         * Return last variable or the default value
         */
        private String getLastValue(String def) {
            Operator l = function.last();
            if(l instanceof Variable)
                return ((Variable) l).getName();
            else
                return def;
        }

        /**
         * Look for available variables
         */
        private String seekVariables(Joint source) {
            if(source != null) {
                for (Wire wire : source.getWires()) {
                    Joint far = source.getLink(wire);
                    if (far != null) {
                        String name = namespace.get(far.getOwner().getID());
                        if (name != null) return name;
                    }
                }
            }
            return null;
        }


        /**
         * Get linked block from first non-empty joint of the given type from source block
         */
        private Block otherSideOf(Block source, int jointType) {
            return otherSideOf(jointOf(source, jointType));
        }
        private Block otherSideOf(Joint joint) {
            Joint far = otherSideJointOf(joint);
            return far == null ? null : far.getOwner();
        }
        private Joint otherSideJointOf(Joint joint) {
            if(joint != null) {
                for (Wire wire : joint.getWires()) {
                    Joint far = joint.getLink(wire);
                    if (far != null) return far;
                }
            }
            return null;
        }
        /**
         * Get first non-empty or last empty joint of given type from source block
         */
        private Joint jointOf(Block source, int jointType) {
            Joint joint = null;
            for(Joint j: source.getJoints()) {
                if(j.getActionType() == jointType) {
                    joint = j;
                    for(Wire wire: joint.getWires())
                        if(joint.getLink(wire) != null) break;
                }
            }
            return joint;
        }
        /**
         * Get alternative node of type
         */
        private Joint alternative(Block source, Joint sample) {
            for(Joint joint: source.getJoints()) {
                if(joint.getActionType() == sample.getActionType()) {
                    if(joint != sample) {
                        return joint;
                    }
                }
            }
            return null;
        }
    }
}
