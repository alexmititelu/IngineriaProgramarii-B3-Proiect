/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.util.Scanner;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import controller.*;
import static entity.Albums_.name;
import java.util.ArrayList;
import java.lang.String;

/**
 *
 * @author Eduard
 */
public class AlbumManager {
    
    static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("MusicAlbumsPU");

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Input command:");
            String command = scanner.nextLine();
            if (command.equals("exit")) break;
            String[] params = command.trim().split("\\s+");
            switch (params[0]) {
                case "create-artist":
                createArtist(params[1]);
             //the artist name
                    break;
                case "create-album":
                    createAlbum(params[1], params[2]); //the album name and the artist name
                    break;
                case "list-albums":
                    listAlbums(params[1]); //the artist name
                    break;				
            }
        }
    }
    private void createArtist(String artistName) {
		ArrayList<String> artists = new ArrayList<String>();
                artists.add(artistName);
    }
    private void createAlbum(String albumName, String artistName) {
		ArrayList<String> albums = new ArrayList<String>();
                ArrayList<String> artistsName = new ArrayList<String>();
                albums.add(albumName);
                artistsName.add(artistName);
    }
    private void listAlbums(String artistName) {
		
    }
    
    public static void main(String args[]) {
        new AlbumManager().run();
    }
}
