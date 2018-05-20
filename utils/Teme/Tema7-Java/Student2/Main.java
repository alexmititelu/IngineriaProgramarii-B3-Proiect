/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordgame;

/**
 *
 * @author Eduard
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.setBag(new Bag());
        game.setBoard(new Board());
        game.addPlayer(new Player("Marcel"));
        game.addPlayer(new Player("Costel"));
        game.addPlayer(new Player("Viorel"));
        game.start();
    }
    
}
