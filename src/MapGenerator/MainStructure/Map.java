package MapGenerator.MainStructure;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;


public class Map {
    private ArrayList<Room> rooms_list;
    private ArrayList<ArrayList<Field>> field_grid;
    private int current_level = 0;
    private int width;
    private int height;
    private Random rand;

    // ***********************CONSTRUCTORS ****************

    public Map() {
        rand = new Random();
        height = 300;
        width = 300;
        init(width,height);
    }

    public Map(Random rand ,int width, int height) {
        this.rand=rand;
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

    public Set<Room> getRoomsNeighbours(int roomID){
        Set<Room> s = rooms_list.get(roomID).getNeighbours();
        return rooms_list.get(roomID).getNeighbours();
    }

    // ****************PUBLIC METHODS *********************
    public boolean addRoom(int x,int y,int width, int height){
        boolean res = rooms_list.add(new Room(this,rand,rooms_list.size(),x,y,current_level,width,height));
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

    public boolean addDoor(int Room1, int Room2){
        return rooms_list.get(Room1).addDoorWith(rooms_list.get(Room2));
    }

    void prepareData(){
        rooms_list.stream().forEach(c->c.clearNeighbours());

        for (int row = 0; row < height; ++row) {
            for (int col = 0; col < width; ++col) {
                Field field = field_grid.get(row).get(col);
                if( field !=null){
                    field.calcState();
                    if(field.getState() == Field.State.WALL && /*field.getTopOwner() != rooms_list.get(0) &&*/
                            (field.getWallType() == Field.Wall.HORIZONTAL_LINE ||
                                    field.getWallType() == Field.Wall.VERTICAL_LINE )){
                        // field is at the edge of two rooms and is straight wall with just two neighbouring rooms
                        Room owner = field.getTopOwner();
                        field.getUnique_neighbours().stream().filter(c->c!=owner).
                                forEach(d->owner.addNeighbour(d,field));

                    }
                }
            }

        }
    }
    public String printMapaData(){
        StringBuilder block= new StringBuilder();
        for(int row=0;row<getHeight();++row){
            for(int col=0;col<getWidth();++col){
                switch(getField(row, col).getState()){

                    case WALL:block.append(getField(row, col).getWallType().ordinal() + ":");
                        break;
                    case EMPTY:
                    case IN_ROOM:block.append("-2:");
                        break;
                    case DOOR:block.append("-1:");
                        break;
                }
            }
            block.deleteCharAt(block.length()-1);
            block.append("\n");

        }
        return block.toString();
    }
    @Override
    public String toString() {
        if(field_grid.size() != 0){
            StringBuilder outp = new StringBuilder();
            for(int row=0;row<height;++row){
                for(int col=0; col<width;++col){
                    outp.append(field_grid.get(row).get(col).toString());
                }
                outp.append("\n");
            }
            return outp.toString();
        }
        return "";

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

    public void clearWallData(){
        rooms_list.stream().forEach(c->c.clearNeighbours());
    }



}
