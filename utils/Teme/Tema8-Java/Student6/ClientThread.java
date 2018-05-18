package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket = null;
    private final GameServer server;

    // Create the constructor that receives a reference to the server and to the client socket
    public ClientThread(Socket socket, GameServer gameServer) {
        this.socket = socket;
        this.server = gameServer;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //client -> server stream
            String request = in.readLine();
            String response = execute(request);
            PrintWriter out = new PrintWriter(socket.getOutputStream()); //server -> client stream
            out.println(response);
            out.flush();
            socket.close(); //... usse try-catch-finally to handle the exceptions!
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    private String execute(String request) {
         // display the message: "Server received the request ... "
        System.out.println("Server received the request..." + request);
        return request;
    }
}