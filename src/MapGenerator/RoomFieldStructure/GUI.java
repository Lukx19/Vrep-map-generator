package MapGenerator.RoomFieldStructure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeListener;

public class GUI extends JFrame{
    JPanel map_frame;
    JPanel main_frame;
    MapGui map;
    public GUI(String title) throws HeadlessException {
        super(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800,600);

        main_frame = new JPanel(new BorderLayout());
        main_frame.setSize(800,600);


        map_frame = new JPanel(null,true);
        map_frame.setSize(300,300);
        map = new MapGui(map_frame,300,300);
        map.addRoom(10,10,20,20);
        map.addRoom(30,30,40,40);
        map.addRoom(0,0,40,40);


        main_frame.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "right");
        main_frame.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "left");
        main_frame.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "up");
        main_frame.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "down");
        main_frame.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, 0, false), "rise");
        main_frame.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, 0, false), "lower");
        main_frame.getActionMap().put("right", new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        map.moveRight();
                    }
                });
        main_frame.getActionMap().put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map.moveLeft();
            }
        });
        main_frame.getActionMap().put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map.moveDown();
            }
        });
        main_frame.getActionMap().put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map.moveUp();
            }
        });
        main_frame.getActionMap().put("rise", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map.riseRoom();
            }
        });
        main_frame.getActionMap().put("lower", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map.lowerRoom();
            }
        });

        main_frame.add(BorderLayout.CENTER, map_frame);
        add(main_frame);
        setVisible(true);

    }
}
