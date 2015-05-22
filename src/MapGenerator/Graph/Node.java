package MapGenerator.Graph;

import java.util.ArrayList;

/**
 * Node in the graph.
 * @param <T> data in the node
 */
public class Node<T>{
    private int ID;
    private T data;
    private ArrayList<Edge<T>> edges;

    /**
     * @param ID of the node
     * @param data stored in the node
     */
    public Node(int ID,T data) {
        this.ID=ID;
        this.data = data;
        edges= new ArrayList<>();
    }

    /**
     * @param edge adds edge to the node's edges
     */
    public void addEdge(Edge<T> edge){
        edges.add(edge);

    }

    /**
     * @return count of edges heading from or to this node
     */
    public int getEdgesCount(){
        return edges.size();
    }

    /**
     * @return all edges  heading from of to this node
     */
    public ArrayList<Edge<T>> getEdges(){
        return edges;
    }

    /**
     * @return data block stored in the node
     */
    public T getData(){
        return data;
    }

    /**
     * @return ID of the node
     */
    public int getID(){
        return ID;
    }

    /**
     * @return String ID of the node
     */
    @Override
    public String toString() {
        return String.valueOf(ID);
    }
}