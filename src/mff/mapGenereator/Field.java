package mff.mapGenereator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

import static java.util.Collections.sort;


public class Field {
    public enum State{WALL,EMPTY,IN_ROOM,DOOR}
    private Map map;
    private State state;
    private int posX;
    private int posY;
    private int unique_neighbours;
    private Room[] neighbours;
    private PriorityQueue<Room> owners; // 0 - northWest 1 -northEast 2-SouthEast 3- southWest

    public Field(Map m,int x,int y,int level) {
        this.map=m;
        posX=x;
        posY=y;
        //this.level=level;
        neighbours = new Room[4];
        state=State.EMPTY;
        unique_neighbours=0;
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

//    public int getLevel(){
//        return level;
//    }
//    public void setLevel(int level){
//        this.level=level;
//    }
    public State getState(){
        return state;
    }

    public void calcState(){
        if(unique_neighbours == 1){
            // this field is inside of the room
            state=State.IN_ROOM;
        }else if(unique_neighbours > 1){
            // this field has multiple owners -> this field is at the boundary of rooms
            state= State.WALL;
        }else{
            // no owner of this field
            state= State.EMPTY;
        }
    }

    public void refresh(){
        neighbours[0]=map.getField(posX-1,posY-1).getTopOwner();
        neighbours[1]=map.getField(posX+1,posY-1).getTopOwner();
        neighbours[2]=map.getField(posX+1,posY+1).getTopOwner();
        neighbours[3]=map.getField(posX-1,posY+1).getTopOwner();
    }

    public int getLowerLevel(int level) {
        int lower=Integer.MIN_VALUE;
        for (Room room : owners) {
            if(room.getLevel()>lower && room.getLevel() <level){
                lower = room.getLevel();
            }
        }
        if(lower == Integer.MIN_VALUE)
            return level;
        else
            return lower;
    }

    public int getHigherLevel(int level){
        int higher=Integer.MAX_VALUE;
        for (Room room : owners) {
            if(room.getLevel()<higher && room.getLevel() > level){
                higher = room.getLevel();
            }
        }
        if(higher == Integer.MAX_VALUE)
            return level;
        else
            return higher;
    }


}
