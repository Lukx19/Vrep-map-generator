package MapGenerator.RoomFieldStructure.Graph;

import java.util.ArrayList;

public class Node<T>{
    int ID;
    private T data;
    private ArrayList<Edge<T>> edges;

    public Node(int ID,T data) {
        this.ID=ID;
        this.data = data;
        edges= new ArrayList<>();
    }

    public void addEdge(Edge<T> edge){
        edges.add(edge);

    }

    public int getEdgesCount(){
        return edges.size();
    }
    public ArrayList<Edge<T>> getEdges(){
        return edges;
    }
    public T getData(){
        return data;
    }
    public int getID(){
        return ID;
    }

    @Override
    public String toString() {
        return String.valueOf(ID);
    }
}