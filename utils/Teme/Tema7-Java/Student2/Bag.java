/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordgame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Eduard
 */
public class Bag {
    private final Queue<Character> letters = new LinkedList<>();
    public Bag() {
        char letter;
        for(letter='a';letter<='z';letter++){
            letters.add(letter);
        }
    } // Add all the letters from 'a' to 'z' in the bag.
    public synchronized List<Character> extractLetters(int howMany) {
        // Replace the dots so that the bag is thread-safe
        List<Character> extracted = new ArrayList<>();
        for (int i = 0; i < howMany; i++) {
            if (letters.isEmpty()) break;
            extracted.add(letters.poll());
        }
        return extracted;
    }
}
