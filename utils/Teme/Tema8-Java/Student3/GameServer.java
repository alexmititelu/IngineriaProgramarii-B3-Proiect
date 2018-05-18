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
public class GameServer {
    private static final int PORT = 8100;
    private ServerSocket serverSocket;
    private boolean running = false;

}
public static void main(String[] args)  {
        GameServer server = new GameServer();
        server.init();
        server.waitForClients(); //... handle the exceptions!
    }
    ... // Implement the init() method: create the serverSocket and set running to true
    ... // Implement the waitForClients() method: while running is true, create a new socket for every incoming client and start a ClientThread to execute its request.
	
    public void stop() throws IOException {
        this.running = false;
        serverSocket.close();
    }