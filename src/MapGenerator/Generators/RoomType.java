package MapGenerator.Generators;

/**
 * Wrapper for different room types
 * @author Lukas Jelinek
 */
public class RoomType {

    int width;
    int height;

    /**
     * @param width width of the room
     * @param height height of the room
     */
    public RoomType(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * @return room's width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width sets new room width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return room's height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height sets new room height
     */
    public void setHeight(int height) {
        this.height = height;
    }

}
