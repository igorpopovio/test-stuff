package trivia;

import java.util.ArrayList;
import java.util.List;

import static trivia.Logger.log;

public class Game {
    public static final int MAX_NUMBER_OF_PLAYERS = 6;
    public static final int MAX_NUMBER_OF_PLACES = 12;

    List<Player> players = new ArrayList<>();
    int currentPlayer = -1;

    QuestionBoard board;

    int[] places = new int[MAX_NUMBER_OF_PLAYERS];

    public Game() {
        initializeBoardWithQuestions();
    }

    private void initializeBoardWithQuestions() {
        board = new QuestionBoard();
        for (int i = 0; i < 50; i++) {
            board.addQuestion(new Question("Pop", "Pop Question " + i));
            board.addQuestion(new Question("Science", "Science Question " + i));
            board.addQuestion(new Question("Sports", "Sports Question " + i));
            board.addQuestion(new Question("Rock", "Rock Question " + i));
        }
    }

    public void add(Player player) {
        players.add(player);
        log("%s was added", player);
        log("They are player number %d", players.size());
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
        log("%s's new location is %d", currentPlayer(), places[currentPlayer]);
        askQuestion();
    }

    private void updatePlace(int roll) {
        places[currentPlayer] += roll;
        places[currentPlayer] %= MAX_NUMBER_OF_PLACES;
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
        Question question = board.getNextQuestionFor(category);
        log(question.getText());
    }

    private String currentCategory() {
        if (places[currentPlayer] == 0) return "Pop";
        if (places[currentPlayer] == 4) return "Pop";
        if (places[currentPlayer] == 8) return "Pop";
        if (places[currentPlayer] == 1) return "Science";
        if (places[currentPlayer] == 5) return "Science";
        if (places[currentPlayer] == 9) return "Science";
        if (places[currentPlayer] == 2) return "Sports";
        if (places[currentPlayer] == 6) return "Sports";
        if (places[currentPlayer] == 10) return "Sports";
        return "Rock";
    }
}
