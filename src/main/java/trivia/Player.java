package trivia;

import static trivia.Logger.log;

public class Player {
    private String name;
    private int coins;
    private boolean isInPenaltyBox;
    private boolean isGettingOutOfPenaltyBox;

    public Player(String name) {
        this.name = name;
    }

    public void provideCorrectAnswer() {
        coins++;
        log("Answer was correct!!!!");
        log("%s now has %d Gold Coins.", this, coins);
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

    public boolean isAllowedToAnswer() {
        return (isInPenaltyBox() && isGettingOutOfPenaltyBox) ||
                !isInPenaltyBox();
    }

    public int getCoins() {
        return coins;
    }

    @Override
    public String toString() {
        return name;
    }
}
