import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {
    private final static String SERVER_ADDRESS = "127.0.0.1";
    private final static int PORT = 8100;

    private Socket socket = null ;
    private PrintWriter out = null ;
    private BufferedReader in = null ;

    public GameClient()
    {
        try {
            socket = new Socket (SERVER_ADDRESS, PORT);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){

        System.out.println("Scrie 'create' pentru a incepe!");

        try {
            in = new BufferedReader (new InputStreamReader(socket.getInputStream ()));
            out = new PrintWriter (socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true)
        {
            String request = this.readFromKeyboard();

            if (request.equalsIgnoreCase("exit")) {
                break;
            } else
                {
                String r;
                try {
                    out.println(request);
                    out.flush();

                    r = in.readLine();

                    System.out.println(r);


                } catch (IOException e) {
                    e.printStackTrace();
                }
                }
        }

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFromKeyboard() {
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }
}