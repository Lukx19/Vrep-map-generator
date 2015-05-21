package MapGenerator.Vrep;


import MapGenerator.MainStructure.Map;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class VrepSocket {
    private static int PORT=9999;
    private DataOutputStream out_to_server;
    private boolean is_connected = false;
    private String data;
    Map map;
    Socket output_socket;

    public VrepSocket(Map map, int PORT) {
        this.map = map;
        this.PORT = PORT;
    }

    public  void addData(String txt){
        data = txt;
    }

    private void sendBlock(String txt) throws IOException {
       out_to_server.writeBytes(txt);

    }

    public void connect() throws IOException {
        is_connected=false;
        output_socket = new Socket("localhost", PORT);
        out_to_server = new DataOutputStream(output_socket.getOutputStream());
        is_connected=true;

    }

    public void close() throws IOException {
        if(is_connected){
            output_socket.close();
            is_connected=false;
        }

    }

    public void send() throws IOException {
        if(!is_connected) connect();
        if(is_connected && data.length() != 0){
            sendBlock(data);
        }
    }

    public void sendMap() throws IOException {
        addData(map.printMapaData());
        send();
    }

    public static void setPORT(int PORT) {
        VrepSocket.PORT = PORT;
    }

}
