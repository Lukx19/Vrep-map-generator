package MapGenerator.MainStructure;

import MapGenerator.Generators.RoomsGenerator;
import MapGenerator.Generators.DoorGenerator;
import MapGenerator.Vrep.VrepSocket;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;

/**
 *  Creates GUI for visualisation of the map and program control
 */
public class GUI extends JFrame{
    private JPanel map_frame;
    private JPanel main_frame;
    private JPanel bottom_frame;
    private JPanel left_frame;
    boolean map_is_ready=false;
    int ROOM_COUNT =5;
    int VREP_PORT = 9999;
    MapGui map;
    VrepSocket vrep;
    RoomsGenerator room_gen;
    Random rand;

    /**
     * Creates GUI of application
     * @param title Title of the window
     * @throws HeadlessException
     */
    public GUI(String title) throws HeadlessException {
        super(title);
        rand = new Random();
        room_gen = new RoomsGenerator(rand);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000,600);

        main_frame = new JPanel(new BorderLayout());
        main_frame.setSize(800,600);
        left_frame = new JPanel(new GridLayout(20,1));
        left_frame.setSize(50,400);
        bottom_frame = new JPanel(new FlowLayout());

        map_frame = new JPanel(null,true);
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
                    map_is_ready=true;
                }
            }
        });
        JButton send_vrep = new JButton("SEND TO VREP");
        send_vrep.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                if (map_is_ready) {
                    sendToVrep();
                }
            }
        });

        JButton save_map = new JButton("SAVE MAP AS");
        save_map.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(map_is_ready){
                    JFileChooser file_chooser = new JFileChooser("..");
                    if(file_chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
                        File file = file_chooser.getSelectedFile();
                        try {
                            saveMap(file);
                        } catch (IOException e1) {
                            JOptionPane.showMessageDialog(main_frame,
                                    "Saving interrupted",
                                    "Saving error",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                    }
                }
            }
        });

        JButton load_config = new JButton("LOAD CONFIG");
        load_config.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser file_chooser = new JFileChooser("..");
                if(file_chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                    File file = file_chooser.getSelectedFile();
                    try {
                        LoadConfig(file);
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(main_frame,
                                "Opening interrupted",
                                "Saving error",
                                JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });

        JLabel port_label = new JLabel("Vrep port: ");
        JTextField port = new JTextField();
        port.setText("9999");
        port.setSize(30, 10);
        port.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
               VREP_PORT = Integer.parseInt(port.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {}

            @Override
            public void changedUpdate(DocumentEvent e) {}
        });

        JLabel rooms_label = new JLabel("Room count: ");
        JTextField rooms = new JTextField();
        rooms.setText("5");
        rooms.setSize(30, 10);
        rooms.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
               ROOM_COUNT = Integer.parseInt(rooms.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {}

            @Override
            public void changedUpdate(DocumentEvent e) {}
        });

        bottom_frame.add(generate_map);
        bottom_frame.add(add_walls);
        bottom_frame.add(send_vrep);
        bottom_frame.add(save_map);
        bottom_frame.add(load_config);
        left_frame.add(port_label);
        left_frame.add(port);
        left_frame.add(rooms_label);
        left_frame.add(rooms);
        main_frame.add(BorderLayout.CENTER, map_frame);
        main_frame.add(BorderLayout.SOUTH,bottom_frame);
        main_frame.add(BorderLayout.WEST,left_frame);

        add(main_frame);
        setVisible(true);
    }


    private void generateMap(){
        map_frame.removeAll();
        map_frame.setSize(200,200);
        map = new MapGui(map_frame,rand,400,400,20,20);
        map.addRoom(0, 0, 20, 20, Color.LIGHT_GRAY);
        room_gen.setRoom_count(ROOM_COUNT);
        room_gen.setMap(map);
        room_gen.setHeight(20);
        room_gen.setWidth(20);
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

    private void saveMap(File file) throws IOException {
        FileOutputStream fout_stream = new FileOutputStream(file);
        BufferedWriter buff_write = new BufferedWriter(new OutputStreamWriter(fout_stream));
        buff_write.write(map.printMapaData());
        buff_write.flush();
        buff_write.close();
    }

    private void LoadConfig(File file) throws IOException{
        FileInputStream fin_stream = new FileInputStream(file);
        BufferedReader buff_read = new BufferedReader(new InputStreamReader(fin_stream));
        ROOM_COUNT = Integer.parseInt(buff_read.readLine());
        room_gen.clearRoomTypes();
        String line = buff_read.readLine() ;
        while (line != null){
            String[] parts =line.split(":");
            room_gen.addRoomType(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]));
            line = buff_read.readLine();
        }


    }


    private void addDoors(){
        if(map != null){
            map.prepareData();
            DoorGenerator door_gen=new DoorGenerator(rand,map);
            door_gen.calcDoors();
        }

    }
    private void sendToVrep(){
        if(map != null){
            vrep= new VrepSocket(map,VREP_PORT);

            try {
                vrep.connect();

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        "Connection to Vrep not successful",
                        "Connection error",
                        JOptionPane.ERROR_MESSAGE);

            }
            try {
                vrep.sendMap();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        "Data not send to Vrep",
                        "Connection error",
                        JOptionPane.ERROR_MESSAGE);
            }

            try {
                vrep.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        "Connection with Vrep not closed",
                        "Connection error",
                        JOptionPane.ERROR_MESSAGE);
            }

        }

    }
}
