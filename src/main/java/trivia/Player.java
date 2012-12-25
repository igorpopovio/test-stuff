package trivia;

import java.util.Random;

import static trivia.Logger.log;

public class Player {
    private String name;
    private Random random;
    private int coins;
    private boolean isInPenaltyBox;
    private boolean isGettingOutOfPenaltyBox;

    public Player(String name) {
        this.name = name;
    }

    public Player(String name, Random random) {
        this.name = name;
        this.random = random;
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

    public void provideAnswer() {
        if (shouldAnswerCorrectly())
            provideCorrectAnswer();
        else
            provideWrongAnswer();
    }

    private boolean shouldAnswerCorrectly() {
        return random.nextInt(9) != 7;
    }

    private void provideCorrectAnswer() {
        coins++;
        log("Answer was correct!!!!");
        log("%s now has %d Gold Coins.", this, coins);
    }

    private void provideWrongAnswer() {
        log("Question was incorrectly answered");
        moveToPenaltyBox();
    }

    public int getCoins() {
        return coins;
    }

    @Override
    public String toString() {
        return name;
    }
}
