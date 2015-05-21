package MapGenerator.KruskalsAlg;

import MapGenerator.Graph.Edge;
import MapGenerator.Graph.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class KruskalsGraph<T> extends Graph<T>{
    private LinkedList<DisJointSet<T>> sets;
    int setsCounter;

    public KruskalsGraph() {
        super();
        sets = new LinkedList<>();
        setsCounter=0;

    }

    @Override
    public void addNode(T data) {
        // check if this node is not already pressent in the graph
        if(getNode(data) == null) {
            DisJointSet<T> disJointSet = new DisJointSet<>(setsCounter++);
            KruskalsNode<T> node = new KruskalsNode<>(disJointSet.getID(), data);
            nodes.add(node);
            disJointSet.addNode(node);
            sets.add(disJointSet);
        }
    }

    public List<Edge<T>> calcSpanningTree() {
        ArrayList<Edge<T>> spanningTree = new ArrayList<>();
        Collections.sort(edges);
        int i = 0;
        while (i != edges.size() && spanningTree.size() != nodes.size() - 1) {
            Edge<T> e = edges.get(i);
            int setID_from = ((KruskalsNode)e.getFrom()).getDisJointSetID();
            int setID_to = ((KruskalsNode)e.getTo()).getDisJointSetID();
            if(setID_from != setID_to){
                spanningTree.add(e);
                DisJointSet a = sets.get(setID_from);
                DisJointSet b = sets.get(setID_to);
                a.unionWith(b);
            }
            i++;
        }
        return spanningTree;
    }
}
