package MapGenerator.KruskalsAlg;

import MapGenerator.Graph.Node;

public class KruskalsNode<T> extends Node<T> {

    private int disJointSetID;

    public KruskalsNode(int disJointSetID, T data) {
        super(disJointSetID,data);
        this.disJointSetID = disJointSetID;
    }

   public int getDisJointSetID() {
        return disJointSetID;
    }

    public void setDisJointSetID(int disJointSetID) {
        this.disJointSetID = disJointSetID;
    }

}
