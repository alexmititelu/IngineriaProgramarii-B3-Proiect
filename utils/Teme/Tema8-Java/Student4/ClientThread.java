import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class ClientThread extends Thread {
    private Socket socket;
    private final GameServer server;
    private BufferedReader in;
    private PrintWriter out;
    private GameData game;

    public ClientThread(Socket socket) {
        this.socket = socket;
        server = new GameServer();
    }


    public void writeToClient(String response)
    {
        System.out.println("trimit " + response);
        out.println(response);
        out.flush();
    }

    public void run() {

        try {
            in = new BufferedReader (new InputStreamReader (socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        game = null;
        while (true) {
            String response = null;
            try {
                response = in.readLine();
                System.out.println("Clientul a scris: " + response);

                if (response.equalsIgnoreCase("create") && game == null) {
                    out.println("Dati un nume: ");
                    out.flush();

                   String name = in.readLine();


                   out.println("Dati un numar: ");
                   out.flush();

                   String number = in.readLine();

                   game = new GameData();

                   game.init(name, Integer.parseInt(number));
                   game.getRandomMax();

                   out.println("Incepe sa alegi numere!");
                   out.flush();

                    System.out.print("Numarul de ghicit este ");
                    System.out.println(game.getTargetNumber());

                }
                else if (response.equalsIgnoreCase("exit"))
                {
                    if (game != null)
                        writeToClient("Loser! the number was " + game.getTargetNumber());
                    break;
                }

                else
                if (game != null)
                {

                    out.println(game.verdict(Integer.parseInt(response)));
                    out.flush();

                    if (game.isWon())
                    {
                        out.println("finish");
                        out.flush();
                        socket.close();
                    }
                }
                else{
                    out.println("Invalid command");
                    out.flush();
                }


            } catch (IOException e) {
//                e.printStackTrace();
                System.out.println("exiting while");
                break;
            }

//            if (!socket.isConnected()) break;
        }

        try {
            System.out.println("closing socket");
            socket.close(); //... usse try-catch-finally to handle the exceptions!
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Closing client");
        }
    }

}