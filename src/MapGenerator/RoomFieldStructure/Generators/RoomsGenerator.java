package MapGenerator.RoomFieldStructure.Generators;


import MapGenerator.RoomFieldStructure.Map;

import java.util.ArrayList;
import java.util.Random;

public class RoomsGenerator {
    Map map;
    Random rand;
    int width;
    int height;
    int room_count;
    ArrayList<RoomType> room_types;

    public RoomsGenerator(Map map, Random rand, int width, int height, int room_count) {
        this.map = map;
        this.rand = rand;
        this.width = width;
        this.height = height;
        this.room_count = room_count;
        room_types = new ArrayList<>();
        room_types.add(new RoomType(4,4));
        room_types.add(new RoomType(3,5));
        room_types.add(new RoomType(5,3));
        room_types.add(new RoomType(6,4));
        room_types.add(new RoomType(4,6));
        room_types.add(new RoomType(6,6));

    }

    public RoomsGenerator(Random rand){
        this.rand = rand;
        this.width = 20;
        this.height = 20;
        this.room_count = 5;
        room_types = new ArrayList<>();
        room_types.add(new RoomType(4,4));
        room_types.add(new RoomType(3,5));
        room_types.add(new RoomType(5,3));
        room_types.add(new RoomType(6,4));
        room_types.add(new RoomType(4,6));
        room_types.add(new RoomType(6,6));
    }

    public void generateRooms(){
        for(int i = 0; i<room_count;++i){
            // place room somewhere inside room 0 in some distance from edges of this environmental room
            int place_x=rand.nextInt(width-4)+2;
            int place_y = rand.nextInt(height-4)+2;
            RoomType type = room_types.get(rand.nextInt(room_types.size()));
            if(place_x+type.getWidth() >width-2 || place_y+type.getHeight() > height-2){
                // selected room on selected position is oversize - repeat loop
                --i;
            }else{
                map.addRoom(place_x,place_y,type.getWidth(),type.getHeight());
            }

        }
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setRoom_count(int room_count) {
        this.room_count = room_count;
    }

    public void addRoomType(int width,int height){
        room_types.add(new RoomType(width,height));
    }

    public void clearRoomTypes() {
        room_types.clear();
    }
}
