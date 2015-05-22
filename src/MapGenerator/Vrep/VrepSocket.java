package MapGenerator.Vrep;

import MapGenerator.MainStructure.Map;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *  Creates connecton with Vrep server and handles map data sending.
 */
public class VrepSocket {
    private static int PORT=9999;
    private DataOutputStream out_to_server;
    private boolean is_connected = false;
    private String data;
    private Map map;
    private Socket output_socket;

    /**
     * Creates new socket with VREP
     * @param map reference to the map
     * @param PORT Running VREP server PORT
     */
    public VrepSocket(Map map, int PORT) {
        this.map = map;
        this.PORT = PORT;
    }

    /**
     * Adds data block.
     * @param txt data block
     */
    public  void addData(String txt){
        data = txt;
    }

    /**
     * Sends data block to VREP
     * @param txt data to be send
     * @throws IOException unsuccessful send
     */
    private void sendBlock(String txt) throws IOException {
       out_to_server.writeBytes(txt);

    }

    /**
     * Connect to Vrep
     * @throws IOException not connected
     */
    public void connect() throws IOException {
        is_connected=false;
        output_socket = new Socket("localhost", PORT);
        out_to_server = new DataOutputStream(output_socket.getOutputStream());
        is_connected=true;

    }

    /**
     *Close connection with Vrep
     *  @throws IOException closing aborted
     */
    public void close() throws IOException {
        if(is_connected){
            output_socket.close();
            is_connected=false;
        }

    }

    /**
     * Send initialized data block
     * @throws IOException sending aborted
     */
    public void send() throws IOException {
        if(!is_connected) connect();
        if(is_connected && data.length() != 0){
            sendBlock(data);
        }
    }

    /**
     * Sends whole map to Vrep
     *  @throws IOException sending aborted
     */
    public void sendMap() throws IOException {
        addData(map.printMapaData());
        send();
    }


    /**
     * Sets port number
     * @param PORT new port number
     */
    public static void setPORT(int PORT) {
        VrepSocket.PORT = PORT;
    }

}
