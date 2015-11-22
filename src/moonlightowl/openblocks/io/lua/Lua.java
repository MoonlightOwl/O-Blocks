package moonlightowl.openblocks.io.lua;

import moonlightowl.openblocks.Settings;
import moonlightowl.openblocks.Workspace;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Joint;

import java.io.IOException;
import java.io.OutputStream;

import static moonlightowl.openblocks.Blocks.Id.END;
import static moonlightowl.openblocks.Blocks.Id.START;

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
                function.add(current.getOperator());
                // Finita la commedia
                if(current.getBlockId() == END) break;
                // Move to next block
                Block block = null;
                for(Joint joint: current.getJoints()){
                    if(joint.getActionType() == Joint.FROM){
                        Joint next = joint.getLink();
                        if(next != null){
                            block = next.getOwner(); break;
                        }
                    }
                }
                current = block;
                if(current != null && current.getBlockId() == START) break;
            }
        }
    }
}
