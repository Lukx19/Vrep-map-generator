package mff.mapGenereator;

/**
 * Created by lukas on 5/15/15.
 */
public class Field {
    public enum State{WALL,EMPTY,IN_ROOM,DOOR}
    private Map map;
    private State state;
    private int posX;
    private int posY;
    private int level;
    private int owners_count;
    private Room[] owners; // 0 - northWest 1 -northEast 2-SouthEast 3- southWest
    public Field(Map m,int x,int y,int level) {
        this.map=m;
        posX=x;
        posY=y;
        this.level=level;
        owners = new Room[4];
        state=State.EMPTY;
        owners_count=0;
    }

//    public void setField(Room owner, int level) {
//        this.owners.add(owner);
//        this.level=level;
//    }
    public int getOwnerCount(){
        return owners_count;
    }
    public  Room[] getOwners(){
        return owners;
    }

    public void addOwner(Room owner){
        if(owner != null){
            ++owners_count;
            if(map.getField(posX-1,posY-1).getOwners()[2] == owner){
                owners[0]=owner;
            }
            if(map.getField(posX+1,posY-1).getOwners()[3] == owner){
                owners[1]=owner;
            }
            if(map.getField(posX+1,posY+1).getOwners()[0] == owner){
                owners[2]=owner;
            }
            if(map.getField(posX-1,posY-1).getOwners()[1] == owner){
                owners[3]=owner;
            }


        }

    }
   public int getX(){
        return posX;
    }
    public int getY(){
        return posY;
    }

    public int getLevel(){
        return level;
    }
    public void setLevel(int level){
        this.level=level;
    }
    public State getState(){
        return state;
    }

    public void calcState(){
        if(owners_count == 1){
            // this field is inside of the room
            state=State.IN_ROOM;
        }else if(owners_count > 1){
            // this field has multiple owners -> this field is at the boundary of rooms
            state= State.WALL;
        }else{
            // no owner of this field
            state= State.EMPTY;
        }
    }

//    public void refresh(){
//        owners[0]=
//    }


}
