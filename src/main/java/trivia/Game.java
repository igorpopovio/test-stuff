package trivia;

import java.util.List;
import java.util.Random;

import static trivia.Logger.log;

public class Game {
    private RingIterator<Player> players;
    private Player currentPlayer;
    private Board board;
    private Random random;

    public Game(List<String> categories, List<Player> players, Random random) {
        logAddedPlayers(players);
        this.players = new RingIterator<>(players);
        this.board = new Board(categories, players);
        this.random = random;
    }

    private void logAddedPlayers(List<Player> players) {
        for (int i = 0; i < players.size(); i++) {
            log("%s was added", players.get(i));
            log("They are player number %d", i + 1);
        }
    }

    public void roll(int roll) {
        log("They have rolled a %d", roll);
        if (currentPlayer.isInPenaltyBox()) doIfInPenaltyBox(roll);
        else doIfOutOfPenaltyBox(roll);
    }

    public Player nextPlayer() {
        currentPlayer = players.next();
        log("%s is the current player", currentPlayer);
        return currentPlayer;
    }

    public boolean isGameOver() {
        return currentPlayer.getCoins() == 6;
    }

    private void doIfOutOfPenaltyBox(int roll) {
        updatePlace(roll);
        askQuestion();
    }

    private void updatePlace(int roll) {
        board.updatePlace(currentPlayer, roll);
        log("%s's new location is %d", currentPlayer, board.getCurrentPlaceFor(currentPlayer));
    }

    private void askQuestion() {
        Question question = board.provideQuestionFor(currentPlayer);
        log("The category is %s", question.getCategory());
        log(question.getText());

        String answer = currentPlayer.provideAnswerFor(question);
        if (question.isCorrectAnswer(answer)) {
            currentPlayer.giveOneCoin();
            log("Answer was correct!!!!");
            log("%s now has %d Gold Coins.", currentPlayer, currentPlayer.getCoins());
        } else {
            log("Question was incorrectly answered");
            currentPlayer.moveInPenaltyBox();
        }
    }

    private void doIfInPenaltyBox(int roll) {
        if (shouldKeepInPenaltyBox(roll)) {
            currentPlayer.keepInPenaltyBox();
            return;
        }
        currentPlayer.moveOutOfPenaltyBox();
        doIfOutOfPenaltyBox(roll);
    }

    private boolean shouldKeepInPenaltyBox(int roll) {
        return roll % 2 == 0;
    }

    public void run() {
        do {
            nextPlayer();
            roll(rollDie());
        } while (!isGameOver());
    }

    private int rollDie() {
        return random.nextInt(5) + 1;
    }
}
