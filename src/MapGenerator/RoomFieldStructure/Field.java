package MapGenerator.RoomFieldStructure;

import java.util.ArrayList;
import java.util.PriorityQueue;



public class Field {
    public enum State{WALL,EMPTY,IN_ROOM,DOOR}
    private Map map;
    private State state;
    private int posX;
    private int posY;
    private ArrayList<Room> unique_neighbours;
    private Room[] neighbours;
    private PriorityQueue<Room> owners; // 0 - northWest 1- north 2 -northEast 3-eats 4-SouthEast 5-south
    // 6- southWest 7-west

    public Field(Map m,int x,int y,int level) {
        this.map=m;
        posX=x;
        posY=y;
        //this.level=level;
        neighbours = new Room[4];
        state=State.EMPTY;
        unique_neighbours=new ArrayList<>();
        owners = new PriorityQueue<>(4, (r1, r2) -> (r1.getLevel() < r2.getLevel())?-1:1);

    }

    public int getOwnerCount(){
        return owners.size();
    }
    public  Room getTopOwner(){
        return owners.peek();
    }

    public void addOwner(Room owner){
        if(owner != null && !owners.contains(owner)){
            owners.add(owner);
        }
    }

    public void removeOwner(Room room) {
        owners.remove(room);
    }

    public int getX(){
        return posX;
    }
    public int getY(){
        return posY;
    }

    public State getState(){
        return state;
    }
    public void setState(State s){
        state= s;
    }

    public int getNeighboursCount(){
        return unique_neighbours.size();
    }

    public Room[] getNeighboursGrid(){
        return neighbours;
    }

    public ArrayList<Room> getUnique_neighbours(){
        return unique_neighbours;
    }

    public void calcState(){
        prepareData();
        if(unique_neighbours.size() == 1){
            // this field is inside of the room
            state=State.IN_ROOM;
        }else if(unique_neighbours.size() > 1){
            // this field has multiple owners -> this field is at the boundary of rooms
            state= State.WALL;
        }else{
            // no owner of this field
            state= State.EMPTY;
        }
    }



    private void prepareData(){
        if(map.getField(posX-1,posY-1) != null) neighbours[0]=map.getField(posX-1,posY-1).getTopOwner();
        else neighbours[0]=null;
        if(map.getField(posX,posY-1) != null)neighbours[1]=map.getField(posX,posY-1).getTopOwner();
        else neighbours[1]=null;
        if(map.getField(posX+1,posY-1) != null)neighbours[2]=map.getField(posX+1,posY-1).getTopOwner();
        else neighbours[2]=null;
        if(map.getField(posX-1,posY-1) != null)neighbours[3]=map.getField(posX+1,posY).getTopOwner();
        else neighbours[3]=null;
        if(map.getField(posX+1,posY+1) != null)neighbours[4]=map.getField(posX+1,posY+1).getTopOwner();
        else neighbours[4]=null;
        if(map.getField(posX,posY+1) != null)neighbours[5]=map.getField(posX,posY+1).getTopOwner();
        else neighbours[5]=null;
        if(map.getField(posX-1,posY+1) != null)neighbours[6]=map.getField(posX-1,posY+1).getTopOwner();
        else neighbours[6]=null;
        if(map.getField(posX-1,posY) != null)neighbours[7]=map.getField(posX-1,posY).getTopOwner();
        else neighbours[7]=null;

        // calculates how many unique neighbours are at one field
       unique_neighbours = new ArrayList<>();
        for(int i =0;i<8;i+=2){
            final int finalI = i;
            if(unique_neighbours.stream().filter(c->c == neighbours[finalI]).count() == 0)
                unique_neighbours.add(neighbours[i]);
        }

    }

    public Room getLowerRoom(Room this_room) {
        int lower=Integer.MIN_VALUE;
        Room lower_room = null;
        for (Room room : owners) {
            if(room.getLevel()>lower && room.getLevel() < this_room.getLevel()){
                lower = room.getLevel();
                lower_room = room;
            }
        }
        if(lower_room == null)
            return this_room;
        else
            return lower_room;
    }

    public Room getHigherRoom(Room this_room){
        int higher=Integer.MAX_VALUE;
        Room higher_room = null;
        for (Room room : owners) {
            if(room.getLevel()<higher && room.getLevel() > this_room.getLevel()){
                higher = room.getLevel();
                higher_room = room;
            }
        }
        if(higher_room == null)
            return this_room;
        else
            return higher_room;
    }


}
