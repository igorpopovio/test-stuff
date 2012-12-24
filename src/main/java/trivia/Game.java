package trivia;

import java.util.List;

import static trivia.Logger.log;

public class Game {
    private CircularIterator<Player> players;
    private Player currentPlayer;
    private Board board;

    public Game(List<String> categories, List<Player> players) {
        logAddedPlayers(players);
        this.players = new CircularIterator<>(players);
        this.board = new Board(categories, players);
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

    public void advanceToNextPlayer() {
        currentPlayer = players.next();
        log("%s is the current player", currentPlayer);
    }

    public void provideCorrectAnswer() {
        currentPlayer.provideCorrectAnswer();
    }

    public void provideWrongAnswer() {
        log("Question was incorrectly answered");
        currentPlayer.moveToPenaltyBox();
    }

    public boolean isGameOver() {
        return currentPlayer.getCoins() == 6;
    }

    public boolean isAllowedToAnswer() {
        return currentPlayer.isAllowedToAnswer();
    }

    private void doIfOutOfPenaltyBox(int roll) {
        board.updatePlace(currentPlayer, roll);
        log("%s's new location is %d", currentPlayer, board.getCurrentPlaceFor(currentPlayer));
        askQuestion();
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

    private void askQuestion() {
        log("The category is %s", board.getCurrentCategoryFor(currentPlayer));
        log("" + board.provideQuestionFor(currentPlayer));
    }
}
