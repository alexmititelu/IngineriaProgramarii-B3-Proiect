
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class ClientThread extends Thread {
    private Socket socket = null;
    private final GameServer server;

    // Create the constructor that receives a reference to the server and to the client socket
    public ClientThread(Socket sok) {
        this.socket = sok;
        this.server = null;
    }

    public void run() {
        BufferedReader in = null;//client -> server stream
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String request = null;
        try {
            request = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String response = execute(request);
        PrintWriter out = null; //server -> client stream
        try {
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println(response);
        out.flush();
        //... usse try-catch-finally to handle the exceptions!
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println (e); }
    }

    private String execute(String request) {
        System.out.println("Server recived the request...  " + request);
        return "";
    }

}