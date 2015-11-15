package moonlightowl.openblocks.io.lua;

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
 * Core project export module
 */

public class Lua {
    public static boolean export(Workspace workspace, OutputStream stream) throws IOException {
        // Let's get started - search for Start block
        for(Block block: workspace.getBlocks())
            if(block.getBlockId() == START)
                return new Tracer(block, stream).run();
        return false;
    }

    private static class Tracer {
        private Block current;
        private OutputStream stream;

        public Tracer(Block beginFrom, OutputStream stream){
            this.current = beginFrom;
            this.stream = stream;
        }
        public boolean run() throws IOException {
            while(current != null){
                // Finita la commedia
                if(current.getBlockId() == END) break;
                // Translate to Lua code
                stream.write(current.getCode().getBytes());
                stream.write('\n');
                // Move to next block
                Block block = null;
                for(Joint joint: current.getJoints()){
                    if(joint.getType() == Joint.FROM){
                        Joint next = joint.getLink();
                        if(next != null){
                            block = next.getOwner(); break;
                        }
                    }
                }
                current = block;
                if(current != null && current.getBlockId() == START) break;
            }
            return true;
        }
    }
}
