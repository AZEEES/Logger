package com.example.logger;

public class NodeL1 {
    private String mnodeId = "";
    private String mnodeName="";
    private String mlevelLeaf="";
    private String mparentId = "";

    public NodeL1(String nodeId, String nodeName, String levelLeaf, String parent_id){
        mnodeId = nodeId;
        mnodeName = nodeName;
        mlevelLeaf = levelLeaf;
        mparentId = parent_id;
    }

    public String getNodeId(){ return mnodeId; }
    public String getNodeName(){ return mnodeName; }
    public String getLevelLeaf(){ return mlevelLeaf; }
    public String getParentId() { return mparentId; }
}
