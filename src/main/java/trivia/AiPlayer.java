package trivia;

import java.util.Random;

import static trivia.Logger.log;

public class AiPlayer extends Player {
    private Random random;

    public AiPlayer(String name) {
        this(name, new Random());
    }

    public AiPlayer(String name, Random random) {
        super(name);
        this.random = random;
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
        giveOneCoin();
        log("Answer was correct!!!!");
        log("%s now has %d Gold Coins.", this, getCoins());
    }

    private void provideWrongAnswer() {
        log("Question was incorrectly answered");
        moveInPenaltyBox();
    }
}
