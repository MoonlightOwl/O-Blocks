package moonlightowl.openblocks.io;

import moonlightowl.openblocks.Blocks;
import moonlightowl.openblocks.Workspace;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Joint;
import moonlightowl.openblocks.structure.Wire;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * OpenBlocks.JSON
 * Created by MoonlightOwl on 11/12/15.
 * ===
 * Generate & parse project structure to / from JSON
 */
public class JSON {
    public static final String BLOCKS = "blocks", JOINTS = "joints",
            ID = "id", CATEGORY = "category", X = "x", Y = "y",
            LINK_JOINT_ID = "link_jid", LINK_BLOCK_ID = "link_bid";

    @SuppressWarnings("unchecked")
    public static JSONObject generate(Workspace workspace){
        JSONObject project = new JSONObject();

        JSONArray blocks = new JSONArray();
        LinkedList<Block> data = workspace.getBlocks();
        for(int c=0; c<data.size(); c++){
            Block b = data.get(c);
            JSONObject block = new JSONObject();
            block.put(ID, c);
            block.put(X, b.getX());
            block.put(Y, b.getY());
            block.put(CATEGORY, b.getBlockId().id);

            JSONArray joints = new JSONArray();
            for(Joint j: b.getJoints()) {
                JSONObject joint = new JSONObject();
                joint.put(ID, j.getJointID());

                Joint x = j.getLink();
                if(x != null) {
                    joint.put(LINK_JOINT_ID, x.getJointID());
                    joint.put(LINK_BLOCK_ID, data.indexOf(x.getOwner()));
                } else {
                    joint.put(LINK_JOINT_ID, null);
                    joint.put(LINK_BLOCK_ID, null);
                }

                joints.add(joint);
            }
            block.put(JOINTS, joints);

            blocks.add(block);
        }
        project.put(BLOCKS, blocks);
        return project;
    }

    public static void recreate(Workspace workspace, JSONObject data) {
        JSONArray blocks = (JSONArray) data.get(BLOCKS);
        for (Object x : blocks) {
            JSONObject block = (JSONObject) x;
            Block b = Blocks.Id.values()[(int)(long)block.get(CATEGORY)].getInstance()
                    .setPosition((Double) block.get(X), (Double) block.get(Y));
            workspace.addBlock(b);
        }
        LinkedList<Block> workspaceBlocks = workspace.getBlocks();
        for (Object _block : blocks) {
            JSONObject block = (JSONObject) _block;
            JSONArray joints = (JSONArray) block.get(JOINTS);

            for (Object _joint : joints) {
                JSONObject joint = (JSONObject) _joint;

                Long id = (Long) joint.get(ID);
                Long link_jid = (Long) joint.get(LINK_JOINT_ID);
                Long link_bid = (Long) joint.get(LINK_BLOCK_ID);
                if (link_jid != null) {
                    ArrayList<Joint> blockJoints = workspaceBlocks.get((int) (long) block.get(ID)).getJoints();
                    Joint j = null;
                    for(Joint _j: blockJoints)
                            if(_j.getJointID() == id.intValue()){ j = _j; break; }
                    if(j != null) {
                        // Exclude possible duplicates
                        if (j.getWire() == null) {
                            Wire wire = new Wire();
                            j.attachWire(wire);

                            blockJoints = workspaceBlocks.get(link_bid.intValue()).getJoints();
                            j = blockJoints.get(link_jid.intValue());
                            j.attachWire(wire);

                            workspace.addWire(wire);
                        }
                    }
                }
            }
        }
    }
}
