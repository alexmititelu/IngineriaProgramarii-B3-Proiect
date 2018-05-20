/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordgame;

import java.util.HashMap;

/**
 *
 * @author Eduard
 */
public class Board {
    private HashMap<String, String> cuvinte = new HashMap<>();
    public synchronized void addWord(Player player, String word){
        String newLine = System.getProperty("line.separator");
        this.cuvinte.put(player.getName(), word);
        System.out.println(word +newLine+ "Player " + player.getName() + " submitted the word  ");
    }
}
