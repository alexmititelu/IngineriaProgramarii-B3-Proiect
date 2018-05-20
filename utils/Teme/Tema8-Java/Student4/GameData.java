import java.util.Random;

public class GameData {
    private String playerName;
    private int targetNumber;
    private int movesMade;
    private int max;
    private boolean hasWon = false;

    public GameData() {
        targetNumber = 0;
        movesMade = 0;
    }

    public void init(String name, int max){
        playerName = name;
        this.max = max;
    }

    public int getRandomMax() {

        if (targetNumber != 0)
            return targetNumber;

        Random r = new Random();
        targetNumber = r.nextInt(max);
        return targetNumber;
    }

    public void incMoves(){
        movesMade++;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getTargetNumber() {
        return targetNumber;
    }

    public int getMovesMade() {
        return movesMade;
    }

    public int getMax() {
        return max;
    }

    public boolean isWon() {
        return hasWon;
    }

    public String verdict(int number) {

        incMoves();

        if (number > targetNumber)
            return "Too Big! Esti la incercarea cu numarul " + this.getMovesMade();

        if (number == targetNumber)
        {
            hasWon = true;
            return "That's right! Ti-a luat " + movesMade + " incercari!";
        }

        return "Too Small! Esti la incercarea cu numarul " + this.getMovesMade();

    }
}
