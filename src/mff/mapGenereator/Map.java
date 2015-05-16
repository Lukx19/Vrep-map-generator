package mff.mapGenereator;

import java.util.ArrayList;

/**
 * Created by lukas on 5/15/15.
 */
public class Map {
    private ArrayList<Room> rooms_list;
    private ArrayList<Field> field_grid;
    private int current_level = 0;
    private int LEVEL_RANGE = 1000;
    private int width;
    private int height;

    // ***********************CONSTRUCTORS ****************

    public Map() {
        height = 300;
        width = 300;
        init(width,height);
    }

    public Map(int width, int height) {
        this.height=height;
        this.width = width;
        init(width,height);
    }
    // ******************** GETTERS & SETTERS *************
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Field getField(int x,int y){
        return field_grid.get(x+y*width);
    }

    // ****************PUBLIC METHODS *********************
    public boolean addRoom(int x,int y,int width, int height){
        boolean res = rooms_list.add(new Room(this,rooms_list.size(),x,y,current_level,width,height));
        if(res) current_level+=LEVEL_RANGE;
        return res;
    }

    public boolean lowerRoom(int roomID){
        int prev_level=rooms_list.get(roomID).getLevel();
        rooms_list.get(roomID).lower();
        if(prev_level >  rooms_list.get(roomID).getLevel())
            return true;
        else
            return false;
    }

    public boolean riseRoom(int roomID){
        int prev_level=rooms_list.get(roomID).getLevel();
        rooms_list.get(roomID).lower();
        if(prev_level <  rooms_list.get(roomID).getLevel())
            return true;
        else
            return false;
    }

    public void moveRight(int roomID){
        // TODO:  throw exception when wrong roomID received
        rooms_list.get(roomID).moveRight();
    }

    public void moveLeft(int roomID){
        // TODO:  throw exception when wrong roomID received
        rooms_list.get(roomID).moveLeft();
    }

    public void moveUp(int roomID){
        // TODO:  throw exception when wrong roomID received
        rooms_list.get(roomID).moveUp();
    }
    public void moveDown(int roomID){
        // TODO:  throw exception when wrong roomID received
        rooms_list.get(roomID).moveDown();
    }

    // ***************PRIVATE METHODS ********************
    private void init(int width, int height){
        rooms_list = new ArrayList();
        field_grid = new ArrayList();
        for(int i=0;i<width;++i)
            for(int j=0; j<height;++j){
            field_grid.add(new Field(this,i,j,0));
        }
    }



}
