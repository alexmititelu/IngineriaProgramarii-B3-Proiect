/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.game;

/**
 *
 * @author Aurelia
 */
public class ClientThread extends Thread {
    private Socket socket = null;
    private final GameServer server;
    ... // Create the constructor that receives a reference to the server and to the client socket
    public void run() {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //client -> server stream
        String request = in.readLine();
        String response = execute(request);
        PrintWriter out = new PrintWriter(socket.getOutputStream()); //server -> client stream
        out.println(response);
        out.flush();
        socket.close(); //... usse try-catch-finally to handle the exceptions!
    }    
    private String execute(String request) {
        ... // display the message: "Server received the request ... "
    }
}