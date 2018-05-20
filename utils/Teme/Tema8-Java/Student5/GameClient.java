import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.io.*;

public class GameClient {
    private final static String SERVER_ADDRESS = "127.0.0.1";
    private final static int PORT = 8100;
    public static void main(String[] args) throws IOException {
        GameClient client = new GameClient();
        while (true) {
            String request = client.readFromKeyboard();
            if (request.equalsIgnoreCase("exit")) {
                break;
            } else {
                client.sendRequestToServer(request);
            }
        }
    }

    private void sendRequestToServer(String request) throws IOException {
       try( Socket sok = new Socket(SERVER_ADDRESS, PORT);
        PrintWriter out = new PrintWriter(sok.getOutputStream(), true);
        BufferedReader in = new BufferedReader (new InputStreamReader(sok.getInputStream())) ) {
           out.println(request);
       } catch(UnknownHostException e){
           System.err.println("No server listening... " + e);
       }

    }

    private String readFromKeyboard() {
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }
}