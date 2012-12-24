package trivia;

import java.util.ArrayList;
import java.util.List;

import static trivia.Logger.log;

public class Game {
    List<Player> players = new ArrayList<>();
    int currentPlayer = -1;

    private List<String> categories;
    private Board board;

    public Game(List<String> categories) {
        this.categories = categories;
    }

    public void add(Player player) {
        players.add(player);
        log("%s was added", player);
        log("They are player number %d", players.size());
    }

    public void start() {
        board = new Board(categories, players);
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
        String category = currentCategory();
        log("The category is %s", category);
        Question question = board.provideQuestionFor(currentPlayer());
        log(question.getText());
    }

    private String currentCategory() {
        return board.getCurrentCategoryFor(currentPlayer());
    }
}
