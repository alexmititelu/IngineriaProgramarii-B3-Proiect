package wordgame;

import java.util.ArrayList;
import java.util.List;


public class Game extends Thread {
    private Bag bag;
    private Board board;
    //private Dictionary dictionary;
    private final List<Player> players = new ArrayList<>();
    public void addPlayer(Player player) {
        players.add(player);
        player.setGame(this);
    }
    //Create getters and setters
    //Create the method that will start the game: start one thread for each player
    public List<Player> getPlayers(){
        return players;
    }
public void start() {
        for (Player player : players) {
            new Thread((Runnable) player).start();
        }
}

    public Board getBoard() {
        return board;
    }

    void setBag(Bag bag) {
        this.bag=bag;
    }

    void setBoard(Board board) {
        this.board=board;
    }

   public Bag getBag() {
        return bag;
    }

   
    
}
