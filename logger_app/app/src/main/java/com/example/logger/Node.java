package com.example.logger;

public class Node {
    private String mnodeId = "";
    private String mnodeName="";

    public Node(String nodeId,String nodeName){
        mnodeId = nodeId;
        mnodeName = nodeName;
    }

    public String getNodeId(){ return mnodeId; }
    public String getNodeName(){ return mnodeName; }
    public void setNodeId(String nodeId) { mnodeId = nodeId; }
    public void setNodeName(String nodeName) { mnodeName = nodeName; }
}
