package MapGenerator.KruskalsAlg;

import java.util.ArrayList;

/**
 * Special structure for kruskals algorithm.
 * @param <T> Type of data inside nodes
 */
public class DisJointSet<T> {

    private ArrayList<KruskalsNode<T>> nodes;
    private int setID;

    /**
     * @param ID unique identifier of the set
     */
    public DisJointSet(int ID) {
        this.nodes = new ArrayList<>();
        setID = ID;
    }

    /**
     * @param other different set to union nodes with this set
     */
    public void unionWith(DisJointSet<T> other){
        other.getNodes().stream().forEach(c->c.setDisJointSetID(setID));
        nodes.addAll(other.getNodes());
    }

    /**
     * Adds new node to this set
     * @param node Kruskal's node
     */
    public void addNode(KruskalsNode<T> node){
        node.setDisJointSetID(setID);
        nodes.add(node);
    }

    /**
     * @return all nodes in this set
     */
    public ArrayList<KruskalsNode<T>> getNodes(){
        return nodes;
    }

    /**
     * @return ID of the set
     */
    public int getID(){
        return setID;
    }
}
