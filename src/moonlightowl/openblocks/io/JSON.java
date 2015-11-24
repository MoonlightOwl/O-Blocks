package moonlightowl.openblocks.io;

import moonlightowl.openblocks.Blocks;
import moonlightowl.openblocks.Workspace;
import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.Joint;
import moonlightowl.openblocks.structure.Wire;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * OpenBlocks.JSON
 * Created by MoonlightOwl on 11/12/15.
 * ===
 * Generate & parse project structure to / from JSON
 */
public class JSON {
    public static final String BLOCKS = "blocks", JOINTS = "joints", WIRES = "wires",
            ID = "id", CATEGORY = "category", X = "x", Y = "y", MULTI = "multi",
            LINK_JOINT_ID = "link_jid", LINK_BLOCK_ID = "link_bid";

    @SuppressWarnings("unchecked")
    public static JSONObject generate(Workspace workspace){
        // Root object
        JSONObject project = new JSONObject();
        // Blocks array
        JSONArray blocks = new JSONArray();
        LinkedList<Block> _blocks = workspace.getBlocks();
        for(int c = 0; c < _blocks.size(); c++){
            Block _block = _blocks.get(c);
            JSONObject block = new JSONObject();
            block.put(ID, c);
            block.put(X, _block.getX());
            block.put(Y, _block.getY());
            block.put(CATEGORY, _block.getBlockId().id);

            JSONArray joints = new JSONArray();
            for(Joint _joint: _block.getJoints()) {
                JSONObject joint = new JSONObject();
                joint.put(ID, _joint.getJointID());
                joint.put(MULTI, _joint.isMultiwired());

                JSONArray wires = new JSONArray();
                for(Wire _wire: _joint.getWires()) {
                    JSONObject wire = new JSONObject();
                    Joint _link = _joint.getLink(_wire);
                    if (_link != null) {
                        wire.put(LINK_JOINT_ID, _link.getJointID());
                        wire.put(LINK_BLOCK_ID, _blocks.indexOf(_link.getOwner()));
                    } else {
                        wire.put(LINK_JOINT_ID, null);
                        wire.put(LINK_BLOCK_ID, null);
                    }
                    wires.add(wire);
                }
                joint.put(WIRES, wires);

                joints.add(joint);
            }
            block.put(JOINTS, joints);

            blocks.add(block);
        }
        project.put(BLOCKS, blocks);
        return project;
    }

    private static Joint getJointByID(ArrayList<Joint> list, int id){
        for(Joint joint: list)
            if(joint.getJointID() == id) return joint;
        return null;
    }
    private static boolean isLinked(Joint a, Joint b) {
        for(Wire wireA: a.getWires())
            for(Wire wireB: b.getWires())
                if(wireA == wireB) return true;
        return false;
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
                Integer id = ((Long) joint.get(ID)).intValue();

                JSONArray wires = (JSONArray) joint.get(WIRES);
                for(Object _wire: wires){
                    JSONObject wire = (JSONObject) _wire;
                    Integer link_jid = ((Long) wire.get(LINK_JOINT_ID)).intValue();
                    Integer link_bid = ((Long) wire.get(LINK_BLOCK_ID)).intValue();
                    ArrayList<Joint> blockJoints = workspaceBlocks.get((int)(long)block.get(ID)).getJoints();
                    Joint j1 = getJointByID(blockJoints, id);
                    if(j1 != null) {
                        blockJoints = workspaceBlocks.get(link_bid).getJoints();
                        Joint j2 = getJointByID(blockJoints, link_jid);
                        if(j2 != null) {
                            if(!isLinked(j1, j2)) {
                                Wire w = new Wire();
                                j1.attachWire(w);
                                j2.attachWire(w);
                                workspace.addWire(w);
                            }
                        }
                    }
                }
            }
        }
    }
}
