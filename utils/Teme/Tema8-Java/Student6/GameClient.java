package com.company;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class GameClient {
    private final static String SERVER_ADDRESS = "127.0.0.1";
    private final static int PORT = 8100;
    Socket socket = null;
    PrintWriter out = null;
    BufferedReader in = null;
    public static void main(String[] args) throws IOException {
        GameClient client = new GameClient();
        while (true) {
            System.out.println("write a command:");
            String request = client.readFromKeyboard();
            if (request.equalsIgnoreCase("exit")) {
                break;
            } else {
                client.sendRequestToServer(request);
            }
        }
    }
    //Implement the sendRequestToServer method

    public void sendRequestToServer(String request) throws IOException{
        socket = new Socket(SERVER_ADDRESS, PORT);
        out = new PrintWriter(socket.getOutputStream(), true);
        out.println(request);
        out.close();
    }

    private String readFromKeyboard() {
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }
}