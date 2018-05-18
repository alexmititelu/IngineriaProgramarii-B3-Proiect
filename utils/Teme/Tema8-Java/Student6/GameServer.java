package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
    private static final int PORT = 8100;
    private ServerSocket serverSocket;
    private boolean running = false;

    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.init();
        try {
            server.waitForClients(); //... handle the exceptions!
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    // Implement the init() method: create the serverSocket and set running to true

    public void init(){
        this.running = true;

        try {
            this.serverSocket = new ServerSocket(PORT);
        }
        catch (IOException e){
            e.printStackTrace();
            this.running = false;
        }
    }

    // Implement the waitForClients() method: while running is true, create a new socket
    // for every incoming client and start a ClientThread to execute its request.

    public void waitForClients() throws IOException {
        System.out.println("Start the game");
            while (this.running) {
                Socket socket = serverSocket.accept();
                new ClientThread(socket, this).start();
            }
    }

    public void stop() throws IOException {
        this.running = false;
        serverSocket.close();
    }
}