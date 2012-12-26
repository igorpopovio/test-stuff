package trivia;

import java.util.ArrayList;
import java.util.List;

import static trivia.Logger.log;

public class Game {
    private Board board;
    private RingIterator<Player> players;
    private Player currentPlayer;
    private Die die;
    private List<Player> penaltyBox;

    public Game(Board board, List<Player> players, Die die) {
        this.board = board;
        this.players = new RingIterator<>(players);
        this.die = die;
        this.penaltyBox = new ArrayList<>();
    }

    public void run() {
        do {
            nextPlayer();
            roll(die.roll());
        } while (!isGameOver());
    }

    public void nextPlayer() {
        currentPlayer = players.next();
        log("%s is the current player", currentPlayer);
    }

    public void roll(int roll) {
        log("They have rolled a %d", roll);
        if (isInPenaltyBox()) doIfInPenaltyBox(roll);
        else doIfOutOfPenaltyBox(roll);
    }

    public boolean isGameOver() {
        return currentPlayer.getCoins() == 6;
    }

    public boolean isInPenaltyBox() {
        return penaltyBox.contains(currentPlayer);
    }

    private void doIfInPenaltyBox(int roll) {
        if (shouldKeepInPenaltyBox(roll)) {
            keepInPenaltyBox();
            return;
        }
        moveOutOfPenaltyBox();
        doIfOutOfPenaltyBox(roll);
    }

    private void doIfOutOfPenaltyBox(int roll) {
        updatePlace(roll);
        askQuestion();
    }

    private boolean shouldKeepInPenaltyBox(int roll) {
        return roll % 2 == 0;
    }

    private void keepInPenaltyBox() {
        log("%s is not getting out of the penalty box", currentPlayer);
    }

    private void moveOutOfPenaltyBox() {
        log("%s is getting out of the penalty box", currentPlayer);
        penaltyBox.remove(currentPlayer);
    }

    private void updatePlace(int roll) {
        board.updatePlace(currentPlayer, roll);
        log("%s's new location is %d", currentPlayer, board.getCurrentPlaceFor(currentPlayer));
    }

    private void askQuestion() {
        Question question = board.provideQuestionFor(currentPlayer);
        log("The category is %s", question.getCategory());
        log(question.getText());
        checkAnswer(question, currentPlayer.provideAnswerFor(question));
    }

    private void checkAnswer(Question question, String answer) {
        if (question.isCorrectAnswer(answer)) doIfAnswerIsCorrect();
        else doIfAnswerIsWrong();
    }

    private void doIfAnswerIsCorrect() {
        currentPlayer.giveCoins(1);
        log("Answer was correct!!!!");
        log("%s now has %d Gold Coins.", currentPlayer, currentPlayer.getCoins());
    }

    private void doIfAnswerIsWrong() {
        log("Question was incorrectly answered");
        moveInPenaltyBox();
    }

    private void moveInPenaltyBox() {
        penaltyBox.add(currentPlayer);
        log("%s was sent to the penalty box", currentPlayer);
    }
}
