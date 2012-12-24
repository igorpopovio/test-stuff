package trivia;

import java.util.List;

import static trivia.Logger.log;

public class Game {
    int currentPlayer = -1;
    List<Player> players;
    private List<String> categories;
    private Board board;

    public Game(List<String> categories, List<Player> players) {
        this.categories = categories;
        this.players = players;
        logAddedPlayers(players);
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
        if (currentPlayer().isInPenaltyBox()) doIfInPenaltyBox(roll);
        else doIfOutOfPenaltyBox(roll);
    }

    public void advanceToNextPlayer() {
        currentPlayer++;
        currentPlayer %= players.size();
        log("%s is the current player", currentPlayer());
    }

    public void provideCorrectAnswer() {
        currentPlayer().provideCorrectAnswer();
    }

    public void provideWrongAnswer() {
        log("Question was incorrectly answered");
        currentPlayer().moveToPenaltyBox();
    }

    public boolean isGameOver() {
        return currentPlayer().getCoins() == 6;
    }

    public boolean isAllowedToAnswer() {
        return currentPlayer().isAllowedToAnswer();
    }

    private Player currentPlayer() {
        return players.get(currentPlayer);
    }

    private void doIfOutOfPenaltyBox(int roll) {
        updatePlace(roll);
        log("%s's new location is %d", currentPlayer(), board.getCurrentPlaceFor(currentPlayer()));
        askQuestion();
    }

    private void updatePlace(int roll) {
        board.updatePlace(currentPlayer(), roll);
    }

    private void doIfInPenaltyBox(int roll) {
        if (shouldKeepInPenaltyBox(roll)) {
            keepInPenaltyBox();
            return;
        }
        moveOutOfPenaltyBox();
        doIfOutOfPenaltyBox(roll);
    }

    private boolean shouldKeepInPenaltyBox(int roll) {
        return roll % 2 == 0;
    }

    private void keepInPenaltyBox() {
        currentPlayer().keepInPenaltyBox();
    }

    private void moveOutOfPenaltyBox() {
        currentPlayer().moveOutOfPenaltyBox();
    }

    private void askQuestion() {
        log("The category is %s", board.getCurrentCategoryFor(currentPlayer()));
        log("" + board.provideQuestionFor(currentPlayer()));
    }
}
