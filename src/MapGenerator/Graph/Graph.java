package MapGenerator.Graph;


import java.util.ArrayList;
import java.util.Optional;


/**
 * Class for creating new Graph. Creating new nodes and edges.
 * @param <T> Type of the data stored in Node
 */
public class Graph<T> {

    protected ArrayList<Node<T>> nodes;
    protected ArrayList<Edge<T>> edges;

    /**
     * Creates empty graph
     */
    public Graph(){
        nodes = new ArrayList<>();
        edges= new ArrayList<>();
    }

    /**
     * Creates edge from a to b
     * @param a from this node
     * @param b to this node
     * @param weight weight on the edge
     */
    public void addEdge(Node<T> a, Node<T> b, int weight){
        // check if this kind of edge doesn't exists already- no multi-edges
        if(a.getEdges().stream().filter(aa -> aa.getFrom() == b || aa.getTo() == b ).count() == 0){
            Edge<T> edge=new Edge<>(a,b,weight);
            edges.add(edge);
            a.addEdge(edge);
            b.addEdge(edge);
        }
    }

    /**
     * Creates new node.
     * @param data data to be stored in the graph node
     */
    public void addNode(T data){
        // check if this node is not already pressent in the graph
        if(getNode(data) == null){
            Node<T> node = new Node<>(nodes.size()-1,data);
            nodes.add(node);
        }

    }

    /**
     * Finds node which contains data
     * @param data reference to data object
     * @return node which contains data block
     */
    public Node<T> getNode(T data){
        Optional<Node<T>> out = nodes.stream().filter(c->c.getData() == data).findFirst();
        if (out.isPresent()) return out.get();
        else return null;
    }

    /**
     * @return all edges in the Graph
     */
    public ArrayList<Edge<T>> getEdges(){
        return edges;
    }

    /**
     * @param new_edges redefine edges in the Graph as new_edges
     */
    public void addAllEdges(ArrayList<Edge<T>> new_edges){
        edges = new_edges;
    }

    /**
     * @return all nodes in the graph
     */
    public ArrayList<Node<T>> getNodes(){
        return nodes;
    }

    /**
     * @return Graph representation of edges node a to node b
     */
    @Override
    public String toString() {
        StringBuilder outp = new StringBuilder();
        edges.stream().forEach(c->c.toString());
        return outp.toString();
    }
}
