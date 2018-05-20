import java.io.*;
import java.net.*;

public class GameServer {
    private static final int PORT = 8100;
    private ServerSocket serverSocket;
    private boolean running = false;

    public static void main(String[] args)  {
        GameServer server = new GameServer();
        server.init();
        server.waitForClients(); //... handle the exceptions!
    }
    // Implement the init() method: create the serverSocket and set running to true
    // Implement the waitForClients() method: while running is true, create a new socket for every
    // incoming client and start a ClientThread to execute its request.

    public void stop() throws IOException {
        this.running = false;
        serverSocket.close();
    }

    public void init() {
        try {
            serverSocket = new ServerSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
        running = true;
    }
    public void waitForClients() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Waiting for a client...");
            while (running == true) {
                Socket sok = serverSocket.accept();
                new ClientThread(sok).start();
            }
        } catch(IOException e) {
            System.err. println (e);
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}
