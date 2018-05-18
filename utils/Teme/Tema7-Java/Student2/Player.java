package wordgame;

import java.util.List;

public class Player implements Runnable {
    private String name;
    private Game game;
    boolean cuvinteRamase = true;

    public Player(String name){
        this.name=name;
    }
    
    public void setGame(Game game) {
        this.game = game;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Game getGame() {
        return game;
    }
    
    public String getName(){
        return name;
    }
    
    private boolean createSubmitWord() throws InterruptedException {
        List extracted = game.getBag().extractLetters(1);
        if (extracted.isEmpty()) {
            return false;
        }
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            word.append(extracted.get(0));
        }
        game.getBoard().addWord(this, word.toString());
        Thread.sleep(300);
        return true;
	}
    //implement the run() method, that will repeatedly create and submit words
    //implement the toString() method
    
    @Override
    public void run() {
        try {
            while (cuvinteRamase) {  
               cuvinteRamase = createSubmitWord(); 
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
