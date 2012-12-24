package trivia;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static trivia.Logger.log;

public class Game {
    public static final int MAX_NUMBER_OF_PLAYERS = 6;
    public static final int MAX_NUMBER_OF_PLACES = 12;
    public static final int QUESTIONS_PER_CATEGORY = 50;

    List<Player> players = new ArrayList<>();
    int currentPlayer = -1;

    private List<String> categories;
    private QuestionBoard board;

    int[] places = new int[MAX_NUMBER_OF_PLAYERS];
    List<String> placeCategories;

    public Game(List<String> categories) {
        this.categories = categories;
        board = createBoard();
        initializePlaceCategories();
    }

    private void initializePlaceCategories() {
        placeCategories = new ArrayList<>();
        Iterator<String> iterator = new CircularIterator<>(categories);
        for (int i = 0; i < MAX_NUMBER_OF_PLACES; i++)
            placeCategories.add(iterator.next());
    }

    private QuestionBoard createBoard() {
        GeneratedQuestionBoardFactory factory = new GeneratedQuestionBoardFactory(
                categories, QUESTIONS_PER_CATEGORY);
        return factory.createBoard();
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
        return placeCategories.get(places[currentPlayer]);
    }
}
