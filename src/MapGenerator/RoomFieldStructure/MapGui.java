package MapGenerator.RoomFieldStructure;


import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class MapGui extends Map {
    private JPanel map_frame;
    private JPanel rooms_frame;
    private JPanel frame;
    private ArrayList<JButton> rooms;
    private JButton activated;
    private int top,left;
    private int width,height;
    private int STEP = 3;

    public MapGui(JPanel frame,int  width, int height) {
        super(width, height);
        this.frame = frame;
        map_frame = new JPanel(null);
        map_frame.setSize(width,height);

        rooms_frame = new JPanel(new GridLayout(2,width));
        frame.setLayout(new BorderLayout(0,0));
        frame.add(map_frame,BorderLayout.CENTER);
        frame.add(rooms_frame,BorderLayout.NORTH);
        top=frame.getInsets().top;
        left=frame.getInsets().left;
        rooms=new ArrayList<>();
        activated = null;
        this.width=width;
        this.height=height;
    }

    @Override
    public boolean addRoom(int x, int y, int width, int height) {
        JButton current = new JButton(String.valueOf(rooms.size()));
        current.setName(String.valueOf(rooms.size()));
        current.setBackground(Color.RED);
        current.setBorder(new LineBorder(Color.BLACK,2));
        current.setBounds(x*STEP+left,y*STEP+top,width*STEP,height*STEP);
        current.setVisible(true);
        current.setRolloverEnabled(false);
        current.addMouseListener(new MouseInputListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
               if (activated != null)
                    activated.setBackground(Color.RED);
                activated =(JButton) e.getComponent();
                activated.setBackground(Color.BLUE);
                map_frame.updateUI();
            }

            @Override
            public void mousePressed(MouseEvent e) { }

            @Override
            public void mouseReleased(MouseEvent e) {  }

            @Override
            public void mouseEntered(MouseEvent e) {
                JButton in = (JButton) e.getComponent();
                in.setBorder(new EtchedBorder(1, Color.BLUE, Color.cyan));
                map_frame.updateUI();

            }

            @Override
            public void mouseExited(MouseEvent e) {
                JButton in = (JButton) e.getComponent();
                in.setBorder(new LineBorder(Color.BLACK, 2));
                map_frame.updateUI();

            }

            @Override
            public void mouseDragged(MouseEvent e) { }

            @Override
            public void mouseMoved(MouseEvent e) { }
        });

        JButton list_button = new JButton(String.valueOf(rooms.size()));
        list_button.setName(String.valueOf(rooms.size()));
        list_button.setRolloverEnabled(true);
        list_button.addMouseListener(new MouseInputListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int pressed = Integer.parseInt(e.getComponent().getName());
                if (activated != null)
                    activated.setBackground(Color.RED);
                activated = rooms.get(pressed);
                activated.setBackground(Color.BLUE);
                map_frame.updateUI();
            }

            @Override
            public void mousePressed(MouseEvent e) { }

            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) { }

            @Override
            public void mouseExited(MouseEvent e) { }

            @Override
            public void mouseDragged(MouseEvent e) { }

            @Override
            public void mouseMoved(MouseEvent e) { }
        });

        rooms.add(current);
        map_frame.add(current);
        rooms_frame.add(list_button);
        super.addRoom(x, y, width, height);
        refresh();
        return true;

    }

    public void moveRight() {
        if (activated == null)
            return;
        int roomID = Integer.parseInt(activated.getName());
        super.moveRight(roomID);
        Room r = getRoom(roomID);
        activated.setBounds(r.getX()*STEP + left, r.getY()*STEP + top, r.getWidth()*STEP, r.getHeight()*STEP);

    }

    public void moveLeft() {
        if (activated == null)
            return;
        int roomID = Integer.parseInt(activated.getName());
        super.moveLeft(roomID);
        Room r = getRoom(roomID);
        activated.setBounds(r.getX()*STEP + left, r.getY()*STEP + top, r.getWidth()*STEP, r.getHeight()*STEP);

    }

    public void moveUp() {
        if (activated == null)
            return;
        int roomID = Integer.parseInt(activated.getName());
        super.moveUp(roomID);
        Room r = getRoom(roomID);
        activated.setBounds(r.getX()*STEP + left, r.getY()*STEP + top, r.getWidth()*STEP, r.getHeight()*STEP);

    }

    public void moveDown() {
        if (activated == null)
            return;
        int roomID = Integer.parseInt(activated.getName());
        super.moveDown(roomID);
        Room r = getRoom(roomID);
        activated.setBounds(r.getX()*STEP + left, r.getY()*STEP + top, r.getWidth()*STEP, r.getHeight()*STEP);

    }
    public void riseRoom(){
        if (activated == null)
            return;
        int roomID = Integer.parseInt(activated.getName());
        super.riseRoom(roomID);
        refresh();
    }

    public void lowerRoom(){
        if (activated == null)
            return;
        int roomID = Integer.parseInt(activated.getName());
        super.lowerRoom(roomID);
        refresh();
    }

    private void refresh(){
        for(JButton button:rooms){
            Room r =getRoom(Integer.parseInt(button.getName()));
            map_frame.setComponentZOrder(button, getRoomsCount()-r.getLevel()-1);

        }
        map_frame.updateUI();
    }

}
