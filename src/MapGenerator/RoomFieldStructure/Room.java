package MapGenerator.RoomFieldStructure;

import java.util.ArrayList;


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
    public int getHeight(){
        return height;
    }
    public int getWidth(){
        return width;
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
        for(int row=0;row<height;++row)
            for (int col = y; col < width; ++col) {
                if(fields_grid.get(row).get(col) != null){
                    fields_grid.get(row).get(col).removeOwner(this);
                    fields_grid.get(row).get(col).addOwner(this);
                }

            }
    }

    public ArrayList<Room> getNeighbours(){
        return neighbours;
    }
    // ****************PUBLIC METHODS *********************
    private boolean addNeighbour(Room r){
       return neighbours.add(r);
    }
    private boolean removeNeighbour(Room r){
        return neighbours.remove(r);
    }


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
    public void swapWith(Room other){
        int other_level= other.getLevel();
        other.setLevel(level);
        setLevel(other_level);
    }
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

    public void moveRight(){
        for(int row=0;row<height;++row){
            if(fields_grid.get(row).get(0) != null) fields_grid.get(row).get(0).removeOwner(this);
        }
        x++;
        refresh();
    }
    public void moveLeft(){
        for(int row=0;row<height;++row){
            if(fields_grid.get(row).get(width-1) != null) fields_grid.get(row).get(width-1).removeOwner(this);
        }
        x--;
        refresh();
    }
    public void moveUp(){
        for(int col=0;col<width;++col){
            if(fields_grid.get(height-1).get(col) != null) fields_grid.get(height-1).get(col).removeOwner(this);
        }
        y--;
        refresh();
    }
    public void moveDown(){
        for(int col=0;col<width;++col){
            if(fields_grid.get(0).get(col) != null) fields_grid.get(0).get(col).removeOwner(this);
        }
        y++;
        refresh();
    }




}
