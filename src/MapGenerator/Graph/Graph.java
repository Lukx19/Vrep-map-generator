package MapGenerator.Graph;


import java.util.ArrayList;
import java.util.Optional;


public class Graph<T> {

    protected ArrayList<Node<T>> nodes;
    protected ArrayList<Edge<T>> edges;

    public Graph(){
        nodes = new ArrayList<>();
        edges= new ArrayList<>();
    }

    public void addEdge(Node<T> a, Node<T> b, int weight){
        // check if this kind of edge doesn't exists already- no multi-edges
        if(a.getEdges().stream().filter(aa -> aa.getFrom() == b || aa.getTo() == b ).count() == 0){
            Edge<T> edge=new Edge<>(a,b,weight);
            edges.add(edge);
            a.addEdge(edge);
            b.addEdge(edge);
        }
    }
    public void addNode(T data){
        // check if this node is not already pressent in the graph
        if(getNode(data) == null){
            Node<T> node = new Node<>(nodes.size()-1,data);
            nodes.add(node);
        }

    }

    public Node<T> getNode(T data){
        Optional<Node<T>> out = nodes.stream().filter(c->c.getData() == data).findFirst();
        if (out.isPresent()) return out.get();
        else return null;
    }

    public ArrayList<Edge<T>> getEdges(){
        return edges;
    }

    public void addAllEdges(ArrayList<Edge<T>> new_edges){
        edges = new_edges;
    }

    public ArrayList<Node<T>> getNodes(){
        return nodes;
    }

    @Override
    public String toString() {
        StringBuilder outp = new StringBuilder();
        edges.stream().forEach(c->c.toString());
        return outp.toString();
    }
}
