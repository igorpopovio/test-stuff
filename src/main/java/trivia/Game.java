package trivia;

import java.util.ArrayList;
import java.util.List;

import static trivia.Logger.log;

public class Game {
    protected Board board;
    protected RingIterator<Player> players;
    protected Player currentPlayer;
    protected Die die;
    protected List<Player> penaltyBox;

    public Game(Game game) {
        this.board = game.board;
        this.players = game.players;
        this.die = game.die;
        this.penaltyBox = new ArrayList<>();
    }

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

    protected void nextPlayer() {
        currentPlayer = players.next();
        log("%s is the current player", currentPlayer);
    }

    protected void roll(int roll) {
        log("They have rolled a %d", roll);
        if (isInPenaltyBox()) doIfInPenaltyBox(roll);
        else doIfOutOfPenaltyBox(roll);
    }

    protected boolean isGameOver() {
        return currentPlayer.getCoins() == 6;
    }

    protected boolean isInPenaltyBox() {
        return penaltyBox.contains(currentPlayer);
    }

    protected void doIfInPenaltyBox(int roll) {
        if (shouldKeepInPenaltyBox(roll)) {
            keepInPenaltyBox();
            return;
        }
        moveOutOfPenaltyBox();
        doIfOutOfPenaltyBox(roll);
    }

    protected void doIfOutOfPenaltyBox(int roll) {
        updatePlace(roll);
        askQuestion();
    }

    protected boolean shouldKeepInPenaltyBox(int roll) {
        return roll % 2 == 0;
    }

    protected void keepInPenaltyBox() {
        log("%s is not getting out of the penalty box", currentPlayer);
    }

    protected void moveOutOfPenaltyBox() {
        log("%s is getting out of the penalty box", currentPlayer);
        penaltyBox.remove(currentPlayer);
    }

    protected void updatePlace(int roll) {
        board.updatePlace(currentPlayer, roll);
        log("%s's new location is %d", currentPlayer, board.getCurrentPlaceFor(currentPlayer));
    }

    protected void askQuestion() {
        Question question = board.provideQuestionFor(currentPlayer);
        log("The category is %s", question.getCategory());
        log(question.getText());
        checkAnswer(question, currentPlayer.provideAnswerFor(question));
    }

    protected void checkAnswer(Question question, String answer) {
        if (question.isCorrectAnswer(answer)) doIfAnswerIsCorrect();
        else doIfAnswerIsWrong();
    }

    protected void doIfAnswerIsCorrect() {
        currentPlayer.giveCoins(1);
        log("Answer was correct!!!!");
        log("%s now has %d Gold Coins.", currentPlayer, currentPlayer.getCoins());
    }

    protected void doIfAnswerIsWrong() {
        log("Question was incorrectly answered");
        moveInPenaltyBox();
    }

    protected void moveInPenaltyBox() {
        penaltyBox.add(currentPlayer);
        log("%s was sent to the penalty box", currentPlayer);
    }
}
