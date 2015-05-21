package MapGenerator.RoomFieldStructure;

import MapGenerator.RoomFieldStructure.Generators.DoorGenerator;
import MapGenerator.RoomFieldStructure.Generators.RoomsGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GUI extends JFrame{
    JPanel map_frame;
    JPanel main_frame;
    JPanel bottom_frame;
    MapGui map;
    Random rand;
    public GUI(String title) throws HeadlessException {
        super(title);
        rand = new Random();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800,600);

        main_frame = new JPanel(new BorderLayout());
        main_frame.setSize(800,600);

        bottom_frame = new JPanel(new FlowLayout());


        map_frame = new JPanel(null,true);
//        map_frame.setSize(300,300);
//        map = new MapGui(map_frame,100,100);
//        map.addRoom(10,10,20,20);
//        map.addRoom(30,30,40,40);
//        map.addRoom(0,0,40,40);


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

        JButton generate_map = new JButton("GENERATE");
        generate_map.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                generateMap();
            }
        });


        JButton add_walls = new JButton("CALCULATE WALLS AND DOORS");
        add_walls.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                if (map != null){
                    map.prepareData();
                    addDoors();
                    printMap();
                }
            }
        });

        bottom_frame.add(generate_map);
        bottom_frame.add(add_walls);
        main_frame.add(BorderLayout.CENTER, map_frame);
        main_frame.add(BorderLayout.SOUTH,bottom_frame);

        add(main_frame);
        setVisible(true);
    }

    private void generateMap(){
        map_frame.removeAll();
        map_frame.setSize(200,200);
        map = new MapGui(map_frame,rand,400,400,20,20);
        map.addRoom(0, 0, 20, 20, Color.LIGHT_GRAY);
//        map.addRoom(2,3,2,4);
//        map.addRoom(2,2,4,4);
//        map.addRoom(2,2,5,2);
       // map_frame.setVisible(true);
       // main_frame.add(BorderLayout.CENTER, map_frame);
        RoomsGenerator room_gen = new RoomsGenerator(map,rand,20,20,30);
        room_gen.generateRooms();
        addDoors();
        printMap();
    }

    private void printMap(){
        if(map != null){
            //System.out.print(map.toString());
            map.showWallMap();
        }
    }

    private void addDoors(){
        if(map != null){
            map.prepareData();
            DoorGenerator door_gen=new DoorGenerator(rand,map);
            door_gen.calcDoors();
        }

    }
}
