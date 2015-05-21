package MapGenerator.KruskalsAlg;

import java.util.ArrayList;

public class DisJointSet<T> {

    private ArrayList<KruskalsNode<T>> nodes;
    private int setID;

    public DisJointSet(int ID) {
        this.nodes = new ArrayList<>();
        setID = ID;
    }

    public void unionWith(DisJointSet<T> other){
        other.getNodes().stream().forEach(c->c.setDisJointSetID(setID));
        nodes.addAll(other.getNodes());
    }

    public void addNode(KruskalsNode<T> node){
        node.setDisJointSetID(setID);
        nodes.add(node);
    }

    public ArrayList<KruskalsNode<T>> getNodes(){
        return nodes;
    }

    public int getID(){
        return setID;
    }
}
