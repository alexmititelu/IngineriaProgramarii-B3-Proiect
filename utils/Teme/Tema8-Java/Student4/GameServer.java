import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
    private static final int PORT = 8100;
    private ServerSocket serverSocket;
    private boolean running = false;

    public void init() {
        if (!running)
        {
            try {
                serverSocket = new ServerSocket(PORT);
                running = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void waitForClients() {
        while ( running ) {
            System.out.println ("Waiting");
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                new ClientThread(socket).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() throws IOException {
        this.running = false;
        serverSocket.close();
    }
}