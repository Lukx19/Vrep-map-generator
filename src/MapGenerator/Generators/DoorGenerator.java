package MapGenerator.Generators;

import MapGenerator.Graph.Edge;
import MapGenerator.Graph.Node;
import MapGenerator.MainStructure.Map;
import MapGenerator.MainStructure.Room;
import MapGenerator.KruskalsAlg.KruskalsGraph;

import java.util.*;

/**
 *  Generates doors to map.
 *  @author Lukas Jelinek
 */
public class DoorGenerator {
    MapGenerator.MainStructure.Map map;
    KruskalsGraph<Room> rooms_grah;
    Random rand;

    /**
     * Initialize Graph based on rooms and their neighbours.
     * @param rand random generator
     * @param map reference to map
     */
    public DoorGenerator(Random rand, Map map) {
        this.map = map;
        this.rand = rand;
        rooms_grah = new KruskalsGraph<>();
        initGraph();
    }

    private void initGraph(){
        // initialing all the nodes
        for(int i=0;i<map.getRoomsCount();++i){
            Room room = map.getRoom(i);
            rooms_grah.addNode(room);
        }
        // adding all edges
        for(int i=0;i<map.getRoomsCount();++i){
            Room room = map.getRoom(i);
            Node<Room> room_node = rooms_grah.getNode(room);
            for(Room other:room.getNeighbours()){
                int weight = 10;
                if(other.getRoomID()==0 || room.getRoomID() == 0){
                   weight = 100000;
                }
                rooms_grah.addEdge(room_node,rooms_grah.getNode(other),weight);
            }
        }
        ArrayList<Edge<Room>> edges = rooms_grah.getEdges();
        Collections.shuffle(edges,rand);
        rooms_grah.addAllEdges(edges);
    }

    /**
     *  Generates doors to the map of walls.
     */
    public void calcDoors(){
        List<Edge<Room>> doors =  rooms_grah.calcSpanningTree();

        // adding couple more edges as a door to the map even when they were not in spanning tree
        ArrayList<Edge<Room>> unused_edges = new ArrayList<>();
        rooms_grah.getEdges().stream().filter(all->doors.contains(all) == false).
                forEach(unused -> unused_edges.add(unused));
       for(int i = 0; i<unused_edges.size()/4;++i){
           // 1/4 of unused edges between rooms is added as another doors
          Collections.shuffle(unused_edges,rand);
           doors.add(unused_edges.remove(unused_edges.size()-1));
       }

        doors.stream().forEach(c->c.getFrom().getData().addDoorWith(c.getTo().getData()));
    }

}
