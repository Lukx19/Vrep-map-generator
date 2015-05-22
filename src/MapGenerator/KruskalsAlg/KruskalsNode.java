package MapGenerator.KruskalsAlg;

import MapGenerator.Graph.Node;

/**
 * This nodes extends standard implementation of node and adds disJointSetID as a parameter of the node. This parameter is
 * used for adding node to different disJointSet
 * @author Lukas Jelinek
 * @param <T> data type in node
 */
public class KruskalsNode<T> extends Node<T> {

    private int disJointSetID;

    /**
     * @param disJointSetID unique id of the  set where node belong at the beginning
     * @param data node's load
     */
    public KruskalsNode(int disJointSetID, T data) {
        super(disJointSetID,data);
        this.disJointSetID = disJointSetID;
    }

    /**
     * @return Kruskal's set's ID
     */
   public int getDisJointSetID() {
        return disJointSetID;
    }

    /**
     * @param disJointSetID sets disJointSetID unique identifier.
     */
    public void setDisJointSetID(int disJointSetID) {
        this.disJointSetID = disJointSetID;
    }

}
