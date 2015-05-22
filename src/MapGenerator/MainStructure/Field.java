package MapGenerator.MainStructure;

import java.util.ArrayList;
import java.util.PriorityQueue;


/**
 * This class represents one unit of the map. This unit may hold diffrent states and has multiple owners.
 * @author Lukas Jelinek
 */
public class Field {
    /**
     *  Basic state of field.
     */
    public enum State{WALL,EMPTY,IN_ROOM,DOOR}

    /**
     *  If state of field is WALL then one of these wall types is set
     */
    public enum Wall{HORIZONTAL_LINE,VERTICAL_LINE,HORIZONTAL_WITH_TOP,HORIZONTAL_WITH_BOTTOM,
        VERTICAL_WITH_RIGHT,VERTICAL_WITH_LEFT,
        LEFT_TOP_CORNER,RIGHT_TOP_CORNER,RIGHT_BOTTOM_CORNER,
        LEFT_BOTTOM_CORNER,CROSS}
    private Map map;
    private State state;
    private Wall wall_type;
    private int posX;
    private int posY;
    private ArrayList<Room> unique_neighbours;
    private Room[] neighbours; // 0 - northWest 1 -northEast 3-SouthEast 4-southWest 5-north 6 east 7south 8 west
    private PriorityQueue<Room> owners;


    /**
     *  Creates new field
     * @param m reference to whole map
     * @param x horizontal position in map's grid
     * @param y vertical position in maps's grid
     */
    public Field(Map m,int x,int y) {
        this.map=m;
        posX=x;
        posY=y;
        //this.level=level;
        neighbours = new Room[8];
        state=State.EMPTY;
        unique_neighbours=new ArrayList<>();
        owners = new PriorityQueue<>(4, (r1, r2) -> (r1.getLevel() > r2.getLevel())?-1:1);

    }


    /**
     *  The owner of this field which has the highest level in room hierarchy is returned.
     * @return  reference to the room at the top.
     */
    public  Room getTopOwner(){
        return owners.peek();
    }

    /**
     * Adds new owner to this field
     * @param owner reference to the room which is added as co-owner of this field
     */
    public void addOwner(Room owner){
        if(owner != null && !owners.contains(owner)){
            owners.add(owner);
        }
    }

    /**
     * Returns neighbour around this field
     * @param i 0 - northWest 1 -northEast 3-SouthEast 4-southWest
     * @return
     */
    public Room getNeighbour(int i){
        return neighbours[i];
    }

    /**
     * Removes owner from this field
     * @param room Room which should be removed.
     */
    public void removeOwner(Room room) {
        owners.remove(room);
    }

    /**
     * @return x position in map's grid
     */
    public int getX(){
        return posX;
    }

    /**
     * @return y position in map's grid
     */
    public int getY(){
        return posY;
    }

    /**
     *  State of this field based on position in the room
     * @return state of field [DOOR,WALL,EMPTY,IN_ROOM]
     */
    public State getState(){
        return state;
    }

    /**
     * @param s set state s
     */
    public void setState(State s){
        state= s;
    }

    /**
     * @return type of WALL is returned or null if this field doesn't have state WALL
     */
    public Wall getWallType(){
        return wall_type;
    }

    /**
     * Unique neighbours are all neighbouring rooms around this field without repetition.
     * @return references to all rooms neighbouring with this field
     */
    public ArrayList<Room> getUnique_neighbours(){
        return unique_neighbours;
    }

    /**
     *  Calculate current state of this field.
     */
    public void calcState(){
        prepareData();
        if(neighbours[0] == neighbours[1] && neighbours[1]== neighbours[2] && neighbours[2]==neighbours[3]
                && neighbours[0]==neighbours[3]){
            // this field is inside of the room
            state=State.IN_ROOM;
        }else if(unique_neighbours.size() > 1){
            // this field has multiple owners -> this field is at the boundary of rooms
            state= State.WALL;
            calcWallType();
        }else{
            // no owner of this field
            state= State.EMPTY;
        }
    }

    /**
     *  If this field is WALL then wall type may be calculated.
     */
    private void calcWallType() {
        int[] wall_parts = new int[4]; // 0-top,1-right , 2-bottom 3-left
        for(int i=0;i<4;++i){
            if(neighbours[i] != neighbours[(i+1)%4])
                wall_parts[i]=1;
            else
                wall_parts[i]=0;
        }
        int code = wall_parts[0]*1000+wall_parts[1]*100+wall_parts[2]*10+wall_parts[3];
        switch(code){
            case 1010: wall_type=Wall.VERTICAL_LINE; break;
            case 101: wall_type=Wall.HORIZONTAL_LINE; break;
            case 1111: wall_type=Wall.CROSS; break;
            case 111: wall_type=Wall.HORIZONTAL_WITH_BOTTOM; break;
            case 1101: wall_type=Wall.HORIZONTAL_WITH_TOP; break;
            case 1011: wall_type=Wall.VERTICAL_WITH_LEFT; break;
            case 1110: wall_type=Wall.VERTICAL_WITH_RIGHT; break;
            case 1001: wall_type=Wall.LEFT_TOP_CORNER; break;
            case 1100: wall_type=Wall.RIGHT_TOP_CORNER; break;
            case 11: wall_type=Wall.LEFT_BOTTOM_CORNER; break;
            case 110: wall_type=Wall.RIGHT_BOTTOM_CORNER; break;
        }
    }


    /**
     *  calculates neighbours around this field and fills data for unique neighbours calling.
     */
    private void prepareData(){
        unique_neighbours.clear();
        if(map.getField(posX,posY-1) != null){
            neighbours[0]=map.getField(posX,posY-1).getNeighbour(3);
            neighbours[1]=map.getField(posX,posY-1).getNeighbour(2);
        }
        else{
            neighbours[0]=null;
            neighbours[1]=null;
        }
        if(map.getField(posX+1,posY+1) != null) neighbours[2]=map.getField(posX+1,posY+1).getTopOwner();
        else neighbours[2]=null;
        if(map.getField(posX-1,posY) != null) neighbours[3]=map.getField(posX-1,posY).getNeighbour(2);
        else neighbours[3]=null;

        if(map.getField(posX,posY-1) != null) neighbours[4]=map.getField(posX,posY-1).getTopOwner();
        else neighbours[4]=null;
        if(map.getField(posX+1,posY) != null) neighbours[5]=map.getField(posX+1,posY).getTopOwner();
        else neighbours[5]=null;
        if(map.getField(posX,posY+1) != null) neighbours[6]=map.getField(posX,posY+1).getTopOwner();
        else neighbours[6]=null;
        if(map.getField(posX-1,posY) != null) neighbours[7]=map.getField(posX-1,posY).getTopOwner();
        else neighbours[7]=null;

       // calculates how many unique neighbours are at one field
       unique_neighbours = new ArrayList<>();
        for(int i =0;i<4;++i){
            final int finalI = i;
            //if this neighbor is not in our unique list we add it
            if(unique_neighbours.stream().filter(c->c == neighbours[finalI]).count() == 0)
                unique_neighbours.add(neighbours[i]);
        }

    }

    /**
     * Calculates first room which has lower level than this_room parameter on this field.
     * @param this_room finding room lower than this room
     * @return first lower room than input parameter
     */
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

    /**
     * Calculates first room which has higher level than this_room parameter on this field.
     * @param this_room finding room higher than this room
     * @return first higher room than input parameter
     */
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

    /**
     * @return Prints coordinates of this field e.g. [1,2]
     */
    public String printCoords(){
        return "["+getX()+","+getY()+"] ";
    }

    /**
     * @return String representing this field based on field state and wall type e.g. -|-  for CROSS
     */
    @Override
    public String toString() {
        switch(state){
            case IN_ROOM:
            case EMPTY: return "   ";
            case WALL:
                switch (wall_type){
                    case LEFT_TOP_CORNER: return "-' ";
                    case RIGHT_TOP_CORNER: return " '-";
                    case RIGHT_BOTTOM_CORNER: return " ,-";
                    case LEFT_BOTTOM_CORNER:  return "-, ";
                    case CROSS: return "-|-";
                    case HORIZONTAL_LINE: return "---";
                    case VERTICAL_LINE: return " | ";
                    case HORIZONTAL_WITH_BOTTOM: return "-,-";
                    case HORIZONTAL_WITH_TOP: return "-'-";
                    case VERTICAL_WITH_LEFT: return "-| ";
                    case VERTICAL_WITH_RIGHT: return " |-";
                    default: return "UNKNOWN";
                }
            case DOOR: return " D ";
            default: return "UNKNOWN";
        }
       //return owners.size() >0 ?String.valueOf(this.getTopOwner().getRoomID()): " ";
       //return String.valueOf(getUnique_neighbours().size());

    }
}
