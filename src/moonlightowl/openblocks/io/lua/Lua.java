package moonlightowl.openblocks.io.lua;

import moonlightowl.openblocks.Settings;
import moonlightowl.openblocks.Workspace;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Joint;

import java.io.IOException;
import java.io.OutputStream;

import static moonlightowl.openblocks.Blocks.Id.*;

/**
 * OpenBlocks.Lua
 * Created by MoonlightOwl on 11/13/15.
 * ===
 * Core project export module - parses workspace, builds programs structure and translates to Lua
 */

public class Lua {
    public static boolean export(Workspace workspace, OutputStream stream) throws IOException {
        // Create root structure for project
        Function program = new Function("main");
        // Let's get started - search for Start block
        for(Block block: workspace.getBlocks())
            if(block.getBlockId() == START) {
                new Tracer(block, program).run();
                break;
            }
        // Export
        stream.write(("-- [OcBlocks v"+ Settings.VERSION +" generated code] --"+ Settings.EOL).getBytes());
        stream.write(program.toString().getBytes());
        stream.write("-- [The END] --".getBytes());
        return true;
    }

    private static class Tracer {
        private Block current;
        private Function function;

        public Tracer(Block beginFrom, Function function){
            this.current = beginFrom;
            this.function = function;
        }
        public void run() throws IOException {
            while(current != null){
                // Build structure
                if(current.getBlockId() == IF) {
                    Function plus = new Function("plus"), minus = new Function("minus");
                    Condition condition = new Condition(function.last(), plus, minus);
                    Block plusBlock = otherSideOf(current, Joint.YES);
                    if(plusBlock != null) new Tracer(plusBlock, plus).run();
                    Block minusBlock = otherSideOf(current, Joint.NO);
                    if(minusBlock != null) new Tracer(minusBlock, minus).run();
                    function.add(condition);
                } else function.add(current.getOperator());
                // Finita la commedia
                if(current.getBlockId() == END) break;
                // Or move to the next block
                current = otherSideOf(current, Joint.FROM);
                if(current != null && current.getBlockId() == START) break;
            }
        }

        /**
         * Get linked block from first non-empty joint of the given type from source block
         * */
        private Block otherSideOf(Block source, int jointType) {
            Block block = null;
            for(Joint joint: source.getJoints()){
                if(joint.getActionType() == jointType){
                    Joint far = joint.getLink();
                    if(far != null){
                        block = far.getOwner(); break;
                    }
                }
            }
            return block;
        }
    }
}
