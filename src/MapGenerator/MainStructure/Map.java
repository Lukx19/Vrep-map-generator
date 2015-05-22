package MapGenerator.MainStructure;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;


/**
 *  Base class which holds references to Edge and Room data structure. It handles different formats of map output.
 *  @author Lukas Jelinek
 *
 */
public class Map {
    private ArrayList<Room> rooms_list;
    private ArrayList<ArrayList<Field>> field_grid;
    private int current_level = 0;
    private int width;
    private int height;
    private Random rand;

    // ***********************CONSTRUCTORS ****************

    /**
     *  default room with dimensions 30,30.
     */
    public Map() {
        rand = new Random();
        height = 30;
        width = 30;
        init(width,height);
    }

    /**
     * @param rand random generator used in generation
     * @param width width of the room
     * @param height height of the room
     */
    public Map(Random rand ,int width, int height) {
        this.rand=rand;
        this.height=height;
        this.width = width;
        init(width,height);
    }

    /**
     * @return width of the map
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width new width of the map
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return height of the map
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height new height of the map
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Return specified field reference.
     * @param x collomn
     * @param y row
     * @return reference to specified field
     */
    public Field getField(int x,int y){
        if(x>=width || x<0 || y>=height || y<0)
            return null;
        else
            return field_grid.get(y).get(x);
    }

    /**
     * @param roomID ID of the room
     * @return reference of the room with specified ID
     */
    public Room getRoom(int roomID){
        return rooms_list.get(roomID);
    }

    /**
     * @return amount of created rooms
     */
    public int getRoomsCount(){
        return rooms_list.size();
    }

    public Set<Room> getRoomsNeighbours(int roomID){
        Set<Room> s = rooms_list.get(roomID).getNeighbours();
        return rooms_list.get(roomID).getNeighbours();
    }


    /**
     * Creates new room.
     * @param x collomn location- left top corner of new room
     * @param y row location- left top corner of new room
     * @param width width of the room
     * @param height height of the room
     * @return true if possible to create
     */
    public boolean addRoom(int x,int y,int width, int height){
        boolean res = rooms_list.add(new Room(this,rand,rooms_list.size(),x,y,current_level,width,height));
        if(res) current_level++;
        return res;
    }

    /**
     * Lowers room with specified ID.
     * @param roomID room's ID
     * @return true if success
     */
    public boolean lowerRoom(int roomID){
        int prev_level=rooms_list.get(roomID).getLevel();
        rooms_list.get(roomID).lower();
        return prev_level > rooms_list.get(roomID).getLevel();
    }

    /**
     * Rise one level specified room.
     * @param roomID room's ID
     * @return true if success
     */
    public boolean riseRoom(int roomID){
        int prev_level=rooms_list.get(roomID).getLevel();
        rooms_list.get(roomID).rise();
        return prev_level < rooms_list.get(roomID).getLevel();
    }

    /**
     * Moves selected room one field to the right.
     * @param roomID room's ID
     */
    public void moveRight(int roomID){
        rooms_list.get(roomID).moveRight();
    }

    /**
     * Moves selected room one field to the left.
     * @param roomID room's ID
     */
    public void moveLeft(int roomID){
        rooms_list.get(roomID).moveLeft();
    }

    /**
     * Moves selected room one field up.
     * @param roomID room's ID
     */
    public void moveUp(int roomID){
       rooms_list.get(roomID).moveUp();
    }

    /**
     * Moves selected room one field down.
     * @param roomID room's ID
     */
    public void moveDown(int roomID){
        rooms_list.get(roomID).moveDown();
    }

    /**
     * Swaps these two rooms in rooms hierarchy.
     * @param Room1 room's  1 ID
     * @param Room2 room's 2 ID
     */
   public void swapRooms(int Room1, int Room2){
        rooms_list.get(Room1).swapWith(rooms_list.get(Room2));
    }

    /**
     * Add doors in between selected rooms if possible
     * @param Room1 room's  1 ID
     * @param Room2 room's 2 ID
     * @return true if sucess
     */
    public boolean addDoor(int Room1, int Room2){
        return rooms_list.get(Room1).addDoorWith(rooms_list.get(Room2));
    }

    /**
     *  Calculates states and neighbours for each field
     */
    public void prepareData(){
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

    /**
     * Data block representing map
     * @return String data based on each field state and wall type. One line one row.
     * E.g.: -1:0:1;5:\n6:10:...
     *   -2 - empty field
     *   -1 - door
     *   0 - horizontal line
     *   1 - vertical line
     *   2 - horizontal line with top edge -'-
     *   3 - horizontal line with bottom edge -,-
     *   4 - vertical line with right edge |-
     *   5 - vertical line with left edge -|
     *   6 - left top corner -'
     *   7 - right top corner '-
     *   8 - right down corner ,-
     *   9 - left down corner -,
     *   10 - cross -|-
     */
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

    /**
     * @return Graphical representation of the map. Created from ASCII symbols.
     */
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


    private void init(int width, int height){
        rooms_list = new ArrayList<>();
        field_grid = new ArrayList<>();
        for(int row=0;row<height;++row){
            field_grid.add(new ArrayList<>());
            for(int col=0; col<width;++col){
                field_grid.get(row).add(new Field(this, col, row));
            }
        }

    }

}
