package mff.mapGenereator;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by lukas on 5/15/15.
 */
public class Room {
    private Map map;
    private int roomID;
    private int x,y,z;
    private int width,height;
    private ArrayList<Field> boundaries;
    private ArrayList<Field> spaces;
    private ArrayList<Room> neighbours;



    public Room(Map m,int roomID,int x, int y, int z, int width, int height) {

        this.map = m;
        this.roomID=roomID;
        this.x = x;
        this.y = y;
        this.z = z;
        this.height = height;
        this.width = width;

        for(int i=x;i<width;++i)
            for (int j = y; j < height; ++j) {
                map.setField(i,j,roomID,z);
            }
    }


    // ******************** GETTERS & SETTERS *************
   public void setHeight(int height) {
        this.height = height;
    }
    public void setWidth(int width) {
        this.width = width;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLevel() {
        return z;
    }

    public void setLevel(int z) {
        this.z = z;
    }

    public ArrayList<Room> getNeighbours(){
        return neighbours;
    }
    // ****************PUBLIC METHODS *********************
    private boolean addNeighbour(Room r){
       return neighbours.add(r);
    }
    private boolean removeNeighbour(Room r){
       if(neighbours.remove(r))
            return true;
       else
            return false;
    }

    public void refresh(){

    }

}
