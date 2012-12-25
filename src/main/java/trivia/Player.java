package trivia;

import java.util.Random;

import static trivia.Logger.log;

public abstract class Player {
    private String name;
    private int coins;
    private boolean isInPenaltyBox;
    private boolean isGettingOutOfPenaltyBox;

    public Player(String name) {
        this.name = name;
    }

    public boolean isInPenaltyBox() {
        return isInPenaltyBox;
    }

    public void moveToPenaltyBox() {
        isInPenaltyBox = true;
        log("%s was sent to the penalty box", this);
    }

    public void keepInPenaltyBox() {
        isGettingOutOfPenaltyBox = false;
        log("%s is not getting out of the penalty box", this);
    }

    public void moveOutOfPenaltyBox() {
        isGettingOutOfPenaltyBox = true;
        log("%s is getting out of the penalty box", this);
    }

    public boolean canAnswer() {
        return (isInPenaltyBox() && isGettingOutOfPenaltyBox) ||
                !isInPenaltyBox();
    }

    public abstract void provideAnswer();

    public int getCoins() {
        return coins;
    }

    public void giveOneCoin() {
        coins++;
    }

    @Override
    public String toString() {
        return name;
    }
}
