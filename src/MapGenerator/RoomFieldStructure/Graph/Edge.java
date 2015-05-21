package MapGenerator.RoomFieldStructure.Graph;

import com.sun.istack.internal.NotNull;

public class Edge<T> implements Comparable<Edge<T>> {
    private Node<T> from;
    private Node<T> to;
    private int weight;


    public Edge(Node<T> from, Node<T> to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public Node<T> getFrom() {
        return from;
    }

    public void setFrom(Node<T> from) {
        this.from = from;
    }

    public Node<T> getTo() {
        return to;
    }

    public void setTo(Node<T> to) {
        this.to = to;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    @NotNull
    public int compareTo(Edge<T> other) {
        if(this.getWeight()<other.getWeight())
            return -1;
        else if (this.getWeight() == other.getWeight())
            return 0;
        else
            return 1;
    }

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