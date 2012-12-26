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
    }

    protected void roll(int roll) {
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
    }

    protected void moveOutOfPenaltyBox() {
        penaltyBox.remove(currentPlayer);
    }

    protected void updatePlace(int roll) {
        board.updatePlace(currentPlayer, roll);
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
    }

    protected void doIfAnswerIsWrong() {
        moveInPenaltyBox();
    }

    protected void moveInPenaltyBox() {
        penaltyBox.add(currentPlayer);
    }
}
