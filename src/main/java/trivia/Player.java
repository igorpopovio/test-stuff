package trivia;

import static trivia.Logger.log;

public abstract class Player {
    private String name;
    private int coins;
    private boolean isInPenaltyBox;

    public Player(String name) {
        this.name = name;
    }

    public boolean isInPenaltyBox() {
        return isInPenaltyBox;
    }

    public void moveInPenaltyBox() {
        isInPenaltyBox = true;
        log("%s was sent to the penalty box", this);
    }

    public void keepInPenaltyBox() {
        log("%s is not getting out of the penalty box", this);
    }

    public void moveOutOfPenaltyBox() {
        isInPenaltyBox = false;
        log("%s is getting out of the penalty box", this);
    }

    public boolean canAnswer() {
        return !isInPenaltyBox();
    }

    public abstract String provideAnswerFor(Question question);

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
