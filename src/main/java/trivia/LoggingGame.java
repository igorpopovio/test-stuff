package trivia;

import static trivia.Logger.log;

public class LoggingGame extends Game {
    public LoggingGame(Game game) {
        super(game);
    }

    protected void switchPlayerTurns() {
        super.switchPlayerTurns();
        log("%s is the current player", currentPlayer);
    }

    protected void roll(int roll) {
        log("They have rolled a %d", roll);
        super.roll(roll);
    }

    protected void keepInPenaltyBox() {
        log("%s is not getting out of the penalty box", currentPlayer);
    }

    protected void moveOutOfPenaltyBox() {
        log("%s is getting out of the penalty box", currentPlayer);
        super.moveOutOfPenaltyBox();
    }

    protected void updatePlace(int roll) {
        super.updatePlace(roll);
        log("%s's new location is %d", currentPlayer, board.getCurrentPlaceFor(currentPlayer));
    }

    protected Question provideQuestion() {
        Question question = super.provideQuestion();
        log("The category is %s", question.getCategory());
        log(question.getText());
        return question;
    }

    protected void doIfAnswerIsCorrect() {
        super.doIfAnswerIsCorrect();
        log("Answer was correct!!!!");
        log("%s now has %d Gold Coins.", currentPlayer, currentPlayer.getCoins());
    }

    protected void doIfAnswerIsWrong() {
        log("Question was incorrectly answered");
        moveInPenaltyBox();
    }

    protected void moveInPenaltyBox() {
        super.moveInPenaltyBox();
        log("%s was sent to the penalty box", currentPlayer);
    }
}
