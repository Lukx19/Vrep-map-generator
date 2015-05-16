package mff.mapGenereator;

import java.util.ArrayList;

/**
 * Created by lukas on 5/15/15.
 */
public class Room {
    private Map map;
    private int roomID;
    private int x,y,level;
    private int width,height;
    //private ArrayList<Field> boundaries;
    private ArrayList<ArrayList<Field>> fields_grid;
    private ArrayList<Room> neighbours;



    public Room(Map m,int roomID,int x, int y, int level, int width, int height) {

        this.map = m;
        this.roomID=roomID;
        this.x = x;
        this.y = y;
        this.level = level;
        this.height = height;
        this.width = width;
       refresh();

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
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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
        fields_grid = new ArrayList<>();
        for(int j=y;j<height;++j)
            for (int i = x; i < width; ++i) {
                //map.setField(i,j,roomID,z);
                Field current_field = map.getField(i,j);
                if( current_field!= null){
                    fields_grid.get(j).add(current_field);
                    current_field.addOwner(this);
                }else{
                    fields_grid.get(j).add(null);
                }
            }
    }

    public void rise(int level) {
        this.level = level;
        for(int row=0;row<height;++row)
            for (int col = y; col < width; ++col) {
                fields_grid.get(row).get(col).removeOwner(this);
                fields_grid.get(row).get(col).addOwner(this);
            }
    }

    public void lower(int level) {
        this.level = level;
        for(int row=0;row<height;++row)
            for (int col = y; col < width; ++col) {
                fields_grid.get(row).get(col).removeOwner(this);
                fields_grid.get(row).get(col).addOwner(this);
            }
    }

    public void moveRight(){
        for(int row=0;row<height;++row){
            fields_grid.get(row).get(0).removeOwner(this);
        }
        x++;
        refresh();
    }
    public void moveLeft(){
        for(int row=0;row<height;++row){
            fields_grid.get(row).get(width-1).removeOwner(this);
        }
        x--;
        refresh();
    }
    public void moveUp(){
        for(int col=0;col<width;++col){
            fields_grid.get(height-1).get(col).removeOwner(this);
        }
        y--;
        refresh();
    }
    public void moveDown(){
        for(int col=0;col<width;++col){
            fields_grid.get(0).get(col).removeOwner(this);
        }
        y++;
        refresh();
    }




}
