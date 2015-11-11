package moonlightowl.openblocks.io;

import moonlightowl.openblocks.Workspace;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Joint;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.LinkedList;

/**
 * OpenBlocks.JSON
 * Created by MoonlightOwl on 11/12/15.
 * ===
 * Generate & parse project structure to / from JSON
 */
public class JSON {
    @SuppressWarnings("unchecked")
    public static JSONObject generate(Workspace workspace){
        JSONObject project = new JSONObject();

        JSONArray blocks = new JSONArray();
        LinkedList<Block> data = workspace.getBlocks();
        for(int c=0; c<data.size(); c++){
            Block b = data.get(c);
            JSONObject block = new JSONObject();
            block.put("id", c);
            block.put("x", b.getX());
            block.put("y", b.getY());
            block.put("category", b.getCategory().ordinal());

            JSONArray joints = new JSONArray();
            for(Joint j: b.getJoints()) {
                JSONObject joint = new JSONObject();
                joint.put("id", j.getJointID());

                Joint x = j.getLink();
                if(x != null) {
                    joint.put("link-jid", x.getJointID());
                    joint.put("link-bid", data.indexOf(x.getOwner()));
                } else {
                    joint.put("link-jid", null);
                    joint.put("link-bid", null);
                }

                joints.add(joint);
            }
            block.put("joints", joints);

            blocks.add(block);
        }

        project.put("blocks", blocks);
        return project;
    }
}
