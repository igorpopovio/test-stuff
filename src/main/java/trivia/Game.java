package trivia;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public static final int COINS_TO_WIN = 6;

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
            switchPlayerTurns();
            roll(die.roll());
        } while (!isGameOver());
    }

    protected void switchPlayerTurns() {
        currentPlayer = players.next();
    }

    protected void roll(int roll) {
        if (isInPenaltyBox()) doIfInPenaltyBox(roll);
        else doIfOutOfPenaltyBox(roll);
    }

    protected boolean isGameOver() {
        return currentPlayer.getCoins() == COINS_TO_WIN;
    }

    protected boolean isInPenaltyBox() {
        return penaltyBox.contains(currentPlayer);
    }

    protected void doIfInPenaltyBox(int roll) {
        if (shouldStayInPenaltyBox(roll)) {
            stayInPenaltyBox();
            return;
        }
        moveOutOfPenaltyBox();
        doIfOutOfPenaltyBox(roll);
    }

    protected void doIfOutOfPenaltyBox(int roll) {
        updatePlace(roll);
        askQuestion();
    }

    protected boolean shouldStayInPenaltyBox(int roll) {
        return roll % 2 == 0;
    }

    protected void stayInPenaltyBox() {
    }

    protected void moveOutOfPenaltyBox() {
        penaltyBox.remove(currentPlayer);
    }

    protected void updatePlace(int roll) {
        board.updatePlace(currentPlayer, roll);
    }

    protected void askQuestion() {
        Question question = provideQuestion();
        checkAnswer(question, provideAnswerTo(question));
    }

    protected String provideAnswerTo(Question question) {
        return currentPlayer.provideAnswerFor(question);
    }

    protected Question provideQuestion() {
        return board.provideQuestionFor(currentPlayer);
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
