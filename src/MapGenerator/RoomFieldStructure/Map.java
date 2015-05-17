package MapGenerator.RoomFieldStructure;

import java.util.ArrayList;


public class Map {
    private ArrayList<Room> rooms_list;
    private ArrayList<ArrayList<Field>> field_grid;
    private int current_level = 0;
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
    //TODO: add refresh of rooms and fields when width or height change
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
        if(x>=width || x<0 || y>=height || y<0)
            return null;
        else
            return field_grid.get(y).get(x);
    }

    public Room getRoom(int roomID){
        return rooms_list.get(roomID);
    }

    public int getRoomsCount(){
        return rooms_list.size();
    }

    // ****************PUBLIC METHODS *********************
    public boolean addRoom(int x,int y,int width, int height){
        boolean res = rooms_list.add(new Room(this,rooms_list.size(),x,y,current_level,width,height));
        if(res) current_level++;
        return res;
    }

    public boolean lowerRoom(int roomID){
        int prev_level=rooms_list.get(roomID).getLevel();
        rooms_list.get(roomID).lower();
        return prev_level > rooms_list.get(roomID).getLevel();
    }

    public boolean riseRoom(int roomID){
        int prev_level=rooms_list.get(roomID).getLevel();
        rooms_list.get(roomID).rise();
        return prev_level < rooms_list.get(roomID).getLevel();
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

    public void swapRooms(int Room1, int room2){
        // TODO throw exception if rooms IDs are wrong
        rooms_list.get(Room1).swapWith(rooms_list.get(room2));
    }

    // ***************PRIVATE METHODS ********************
    private void init(int width, int height){
        rooms_list = new ArrayList<>();
        field_grid = new ArrayList<>();
        for(int row=0;row<height;++row){
            field_grid.add(new ArrayList<>());
            for(int col=0; col<width;++col){
                field_grid.get(row).add(new Field(this, col, row, 0));
            }
        }

    }



}
