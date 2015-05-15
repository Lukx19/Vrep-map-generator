package mff.mapGenereator;

import java.util.ArrayList;

/**
 * Created by lukas on 5/15/15.
 */
public class Map {
    private ArrayList<Room> rooms_list;
    private ArrayList<Field> field_grid;
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

    // ****************PUBLIC METHODS *********************
    public void setField(int x, int y, int roomID, int level) {
        if(x> width-1 || y<height-1 || x>0 || y>0){
            // function is accessing valid field
            field_grid.get(x+y*width).setField(rooms_list.get(roomID),level);
        }
    }
    // ***************PRIVATE METHODS ********************
    private void init(int width, int height){
        rooms_list = new ArrayList();
        field_grid = new ArrayList();
        for(int i=0;i<width;++i)
            for(int j=0; j<height;++j){
            field_grid.add(new Field(this,i,j,0,null));
        }
    }


    public Field getField(int x, int y) {
        return field_grid.get(y*width+x);
    }
}
