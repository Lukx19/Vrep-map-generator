package MapGenerator.MainStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;


/**
 * Room consists of multiple fields. Each room object has it level.
 * Room is able to move in map's grid and change its level in room's hierarchy
 * @author Lukas Jelinek
 */
public class Room {
    private Map map;
    private int roomID;
    private int x,y,level;
    private int width,height;
    private HashMap<Room,ArrayList<Field>> boundaries;
    private ArrayList<ArrayList<Field>> fields_grid;
    private Random rand;


    /**
     * @param m reference to map
     * @param rand random generator object
     * @param roomID ID of this room
     * @param x left top corner of room fields
     * @param y left top corner of rooms field
     * @param level initial level in room's hierarchy
     * @param width width of the room
     * @param height height of the room
     */
    public Room(Map m,Random rand,int roomID,int x, int y, int level, int width, int height) {

        this.map = m;
        this.rand = rand;
        this.roomID=roomID;
        this.x = x;
        this.y = y;
        this.level = level;
        this.height = height;
        this.width = width;
        boundaries = new HashMap<>(20);
        refresh();

    }


    /**
     * @param height new height of the room
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return height of the room
     */
    public int getHeight(){
        return height;
    }

    /**
     * @return width of the room
     */
    public int getWidth(){
        return width;
    }

    /**
     * @param width new width of the room
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return X coordinate of room's reference frame in map frame
     */
    public int getX() {
        return x;
    }

    /**
     * @param x sets X coordinate of room's reference frame in map frame
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return Y coordinate of room's reference frame in map frame
     */
    public int getY() {
        return y;
    }

    /**
     * @param y sets Y coordinate of room's reference frame in map frame
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return current level ot he room
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level sets new level of this room
     */
    public void setLevel(int level) {
        this.level = level;
        for(int row=0;row<height;++row)
            for (int col = 0; col < width; ++col) {
                if(fields_grid.get(row).get(col) != null){
                    fields_grid.get(row).get(col).removeOwner(this);
                    fields_grid.get(row).get(col).addOwner(this);
                }

            }
    }

    /**
     * @return ID of the room
     */
    public int getRoomID(){
        return roomID;
    }

    /**
     *  reallocate rooms local field grid
     */
    private void refresh(){
        fields_grid = new ArrayList<>();
        for(int row=0;row<height;++row){
            fields_grid.add(new ArrayList<>());
            for (int col = 0; col < width; ++col) {
                Field current_field = map.getField(x+col,y+row);
                if( current_field!= null){
                    fields_grid.get(row).add(current_field);
                    current_field.addOwner(this);
                }else{
                    fields_grid.get(row).add(null);
                }
            }
        }

    }

    /**
     * @param other exchange this room whit other room. Their levels exchange
     */
    public void swapWith(Room other){
        int other_level= other.getLevel();
        other.setLevel(level);
        setLevel(other_level);
    }

    /**
     *  Rise room lowest possible amount of levels. Based on rooms which are above this room.
     */
    public void rise() {
        int higher_level=level;
        Room higher_room=null;
        Room higher_room_loc;
        for(int row=0;row<height;++row){
            for(int col=0;col<width;++col){
                 if(fields_grid.get(row).get(col) != null){
                    if(higher_level == level){
                        higher_room = fields_grid.get(row).get(col).getHigherRoom(this);
                        higher_level = higher_room.getLevel();
                    }
                     else{
                        higher_room_loc = fields_grid.get(row).get(col).getHigherRoom(this);
                        if(higher_room_loc.getLevel() < higher_level && higher_room_loc.getLevel() != level){
                            // field [row,col] has some other room which is above this room
                            // in lower level than current lowest level
                            higher_level=higher_room_loc.getLevel();
                            higher_room = higher_room_loc;
                        }
                    }
                 }

            }
        }
        if(higher_room != this)
            swapWith(higher_room);

    }

    /**
     *  Lower room lowest possible amount of levels. Based on rooms which are bellow this room.
     */
    public void lower() {
        int lower_level=level;
        Room lower_room = null;
        Room lower_room_loc;
        for(int row=0;row<height;++row){
            for(int col=0;col<width;++col){
                if(fields_grid.get(row).get(col) != null){
                    if(lower_level == level){
                        lower_room = fields_grid.get(row).get(col).getLowerRoom(this);
                        lower_level = lower_room.getLevel();
                    }
                    else{
                        lower_room_loc = fields_grid.get(row).get(col).getLowerRoom(this);
                        if( lower_room_loc.getLevel() > lower_level && lower_room_loc.getLevel() != level){
                            // field [row,col] has some other room which is below this room
                            // in higher level than current lowest level
                            lower_level=lower_room_loc.getLevel();
                            lower_room=lower_room_loc;
                        }
                    }
                }

            }
        }
        if(lower_room != this)
            swapWith(lower_room);
    }

    /**
     *  Moves this room right in map's grid
     */
    public void moveRight(){
        if(x+1 >map.getWidth()-width-2)
            return;
        for(int row=0;row<height;++row){
            if(fields_grid.get(row).get(0) != null) fields_grid.get(row).get(0).removeOwner(this);
        }
        x++;
        refresh();
    }
    /**
     *  Moves this room left in map's grid
     */
    public void moveLeft(){
        if(x-1 <2)
            return;
        for(int row=0;row<height;++row){
            if(fields_grid.get(row).get(width-1) != null) fields_grid.get(row).get(width-1).removeOwner(this);
        }
        x--;
        refresh();
    }
    /**
     *  Moves this room up in map's grid
     */
    public void moveUp(){
        if(y-1 <2)
            return;
        for(int col=0;col<width;++col){
            if(fields_grid.get(height-1).get(col) != null) fields_grid.get(height-1).get(col).removeOwner(this);
        }
        y--;
        refresh();
    }
    /**
     *  Moves this room down in map's grid
     */
    public void moveDown(){
        if(y+1 > map.getHeight()-height-2)
            return;
        for(int col=0;col<width;++col){
            if(fields_grid.get(0).get(col) != null) fields_grid.get(0).get(col).removeOwner(this);
        }
        y++;
        refresh();
    }

    /**
     * Adds new neighbour of this room.
     * @param key reference to neighbouring room
     * @param val field which is at the border of this and key room
     */
    public void addNeighbour(Room key,Field val){
        if (key == null)return;
        if(boundaries.containsKey(key)){
            boundaries.get(key).add(val);
        }else{
            boundaries.put(key,new ArrayList<>());
            boundaries.get(key).add(val);
        }
    }

    /**
     * Removes all neighbours and their fields.
     */
    public void clearNeighbours(){
        boundaries.clear();
    }

    /**
     * @return All neighbouring room references.
     */
    public Set<Room> getNeighbours(){
        return boundaries.keySet();
    }

    /**
     * All fields on the border between this room and r room.
     * @param r room bordering with this room
     * @return all fields at the border of this room and r room
     */
    public ArrayList<Field> getNeighboursFields(Room r){
        if(boundaries.containsKey(r))
            return boundaries.get(r);
        else
            return null;
    }


    /**
     * Add door to one the one of the border fields between this room and othe room
     * @param other neighbour of this rom
     * @return true if success
     */
    public boolean addDoorWith(Room other) {
        ArrayList<Field> all_walls = new ArrayList<>();
        if(boundaries.containsKey(other)) {
           all_walls.addAll(boundaries.get(other));
        }
        if(other.getNeighboursFields(this) != null){
            all_walls.addAll(other.getNeighboursFields(this));
        }
        if(all_walls.size()==0) return false;
        if(all_walls.stream().filter(c->c.getState() == Field.State.DOOR).count() != 0)
            return false;
        int pos=rand.nextInt(all_walls.size());
        all_walls.get(pos).setState(Field.State.DOOR);
        return true;
    }

    /**
     * @return String with information about all neighbours of this room and their border fields
     */
     @Override
    public String toString() {
        StringBuilder outp = new StringBuilder();
        outp.append(roomID);
        outp.append(" room's neighbours: ");
        getNeighbours().stream().forEach(c->outp.append(String.valueOf(c.getRoomID())+","));
        outp.append("\n");
        boundaries.forEach((room, fields) -> {outp.append(room.getRoomID()+"-fields: ");
                                                fields.forEach(field ->outp.append(field.printCoords()));
                                                });
        outp.append("\n");
        return outp.toString();
    }
}
