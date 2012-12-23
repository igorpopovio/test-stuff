package trivia;

import static trivia.Logger.log;

public class Player {
    private String name;
    private int coins;

    public Player(String name) {
        this.name = name;
    }

    public void provideCorrectAnswer() {
        coins++;
        log("Answer was correct!!!!");
        log("%s now has %d Gold Coins.", this, coins);
    }

    public int getCoins() {
        return coins;
    }

    @Override
    public String toString() {
        return name;
    }
}
