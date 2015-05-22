package MapGenerator.Graph;

/**
 * Basic implementation of undirected edge.
 * @author Lukas Jelinek
 * @param <T> data type of node data
 */
public class Edge<T> implements Comparable<Edge<T>> {
    private Node<T> from;
    private Node<T> to;
    private int weight;


    /**
     * @param from start node
     * @param to end node
     * @param weight weight of the edge
     */
    public Edge(Node<T> from, Node<T> to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    /**
     * @return return edge start node
     */
    public Node<T> getFrom() {
        return from;
    }

    /**
     * @param from sets edge starts node
     */
    public void setFrom(Node<T> from) {
        this.from = from;
    }

    /**
     * @return edge end node
     */
    public Node<T> getTo() {
        return to;
    }

    /**
     * @param to edge end node
     */
    public void setTo(Node<T> to) {
        this.to = to;
    }

    /**
     * @return weight on the edge
     */
    public int getWeight() {
        return weight;
    }

    /**
     * @param weight changes edge's weight
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * @param other edge to compare with
     * @return -1 this is less than other; 0 this equals to other; 1 this is greater than other
     */
    @Override
    public int compareTo(Edge<T> other) {
        if(this.getWeight()<other.getWeight())
            return -1;
        else if (this.getWeight() == other.getWeight())
            return 0;
        else
            return 1;
    }

    /**
     * @return String representation of the edge node a to node b
     */
    @Override
    public String toString() {
        StringBuilder outp = new StringBuilder();
        outp.append(from.toString());
        outp.append("->");
        outp.append(to.toString());
        outp.append("\n");
        return outp.toString();
    }
}