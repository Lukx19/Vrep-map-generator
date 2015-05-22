package MapGenerator.MainStructure;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;


/**
 *  Graphical representation of the Map class
 *  @author Lukas Jelinek
 */
public class MapGui extends Map {
    private JPanel map_frame;
    private JPanel rooms_frame;
    private JPanel doors_frame;
    private JPanel frame;
    private ArrayList<JButton> rooms;
    private JButton activated;
    private Color old_color;
    private int top,left;
    private int width,height;
    private int STEP = 10;

    /**
     * @param frame main frame where map should be drawed
     * @param rand random generator
     * @param width width of the gui map
     * @param height height of the gui map
     * @param map_width width of the logical map
     * @param map_height height of the logical map
     */
    public MapGui(JPanel frame,Random rand,int  width, int height, int map_width, int map_height) {
        super(rand,map_width, map_height);
        STEP = width /map_width;
        this.frame = frame;
        JPanel center_frame = new JPanel(new GridLayout(1,2));
        center_frame.setSize(width,height);

        map_frame = new JPanel(null);
        map_frame.setSize(width,height);

        rooms_frame = new JPanel(new GridLayout(4,width));
        doors_frame = new JPanel();

        frame.setLayout(new BorderLayout(0,0));
        center_frame.add(map_frame);
        center_frame.add(doors_frame);
        frame.add(center_frame,BorderLayout.CENTER);
        frame.add(rooms_frame,BorderLayout.NORTH);
        top=center_frame.getInsets().top;
        left=center_frame.getInsets().left;
        rooms=new ArrayList<>();
        activated = null;
        this.width=width;
        this.height=height;
    }

    /**
     * @param x      collomn location- left top corner of new room
     * @param y      row location- left top corner of new room
     * @param width  width of the room
     * @param height height of the room
     * @return true if sucess
     */
    @Override
    public boolean addRoom(int x, int y, int width, int height) {

        return addRoom(x, y, width, height,Color.RED);
    }

    /**
     * @param x      collomn location- left top corner of new room
     * @param y      row location- left top corner of new room
     * @param width  width of the room
     * @param height height of the room
     * @param c Color of the room in gui
     * @return true if sucess
     */
    public boolean addRoom(int x, int y, int width, int height, Color c) {
        old_color = c;
        JButton current = new JButton(String.valueOf(rooms.size()));
        current.setName(String.valueOf(rooms.size()));
        current.setBackground(c);
        current.setBorder(new LineBorder(Color.BLACK,2));
        current.setBounds(x*STEP+left,y*STEP+top,width*STEP,height*STEP);
        current.setVisible(true);
        current.setRolloverEnabled(false);
        current.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (activated != null)
                    activated.setBackground(old_color);
                activated =(JButton) e.getComponent();
                old_color=activated.getBackground();
                activated.setBackground(Color.BLUE);
                map_frame.updateUI();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                JButton in = (JButton) e.getComponent();
                in.setBorder(new EtchedBorder(1, Color.BLUE, Color.cyan));
                map_frame.updateUI();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                JButton in = (JButton) e.getComponent();
                in.setBorder(new LineBorder(Color.BLACK, 2));
                map_frame.updateUI();
            }
        });
        JButton list_button = new JButton(String.valueOf(rooms.size()));
        list_button.setName(String.valueOf(rooms.size()));
        list_button.setRolloverEnabled(true);
        list_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int pressed = Integer.parseInt(e.getComponent().getName());
                if (activated != null)
                    activated.setBackground(old_color);
                activated = rooms.get(pressed);
                old_color = activated.getBackground();
                activated.setBackground(Color.BLUE);
                map_frame.updateUI();
            }
        });

        rooms.add(current);
        map_frame.add(current);
        rooms_frame.add(list_button);
        super.addRoom(x, y, width, height);
        refresh();
        return true;

    }

    /**
     *  Show ASCII graphical representation of the map
     */
    public void showWallMap(){
        doors_frame.removeAll();
        JTextArea txt = new JTextArea();
        txt.setText(toString());
        txt.setFont(new Font("monospaced", Font.PLAIN, 12));
        txt.setLineWrap(false);
        txt.setVisible(true);
        doors_frame.add(txt);
        doors_frame.updateUI();
    }

    /**
     *  Moves room one field to the right graphically and logically
     */
    public void moveRight() {
        if (activated == null)
            return;
        int roomID = Integer.parseInt(activated.getName());
        super.moveRight(roomID);
        Room r = getRoom(roomID);
        activated.setBounds(r.getX()*STEP + left, r.getY()*STEP + top, r.getWidth()*STEP, r.getHeight()*STEP);

    }

    /**
     *  Moves room one field to the left graphically and logically
     */
    public void moveLeft() {
        if (activated == null)
            return;
        int roomID = Integer.parseInt(activated.getName());
        super.moveLeft(roomID);
        Room r = getRoom(roomID);
        activated.setBounds(r.getX()*STEP + left, r.getY()*STEP + top, r.getWidth()*STEP, r.getHeight()*STEP);

    }
    /**
     *  Moves room one field up graphically and logically
     */
    public void moveUp() {
        if (activated == null)
            return;
        int roomID = Integer.parseInt(activated.getName());
        super.moveUp(roomID);
        Room r = getRoom(roomID);
        activated.setBounds(r.getX()*STEP + left, r.getY()*STEP + top, r.getWidth()*STEP, r.getHeight()*STEP);

    }
    /**
     *  Moves room one field down graphically and logically
     */
    public void moveDown() {
        if (activated == null)
            return;
        int roomID = Integer.parseInt(activated.getName());
        super.moveDown(roomID);
        Room r = getRoom(roomID);
        activated.setBounds(r.getX()*STEP + left, r.getY()*STEP + top, r.getWidth()*STEP, r.getHeight()*STEP);

    }
    /**
     *  Moves room one level up in hierarchy graphically and logically
     */
    public void riseRoom(){
        if (activated == null)
            return;
        int roomID = Integer.parseInt(activated.getName());
        super.riseRoom(roomID);
        refresh();
    }
    /**
     *  Moves room one level down in hierarchy graphically and logically
     */
    public void lowerRoom(){
        if (activated == null)
            return;
        int roomID = Integer.parseInt(activated.getName());
        super.lowerRoom(roomID);
        refresh();
    }

    // /********************* PRIVATE METHODS *****************************
    private void refresh(){
        for(JButton button:rooms){
            Room r =getRoom(Integer.parseInt(button.getName()));
            map_frame.setComponentZOrder(button, getRoomsCount()-r.getLevel()-1);

        }
        map_frame.updateUI();
    }


}
