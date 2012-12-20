package trivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static trivia.Logger.log;

public class Game {
    public static final int MAX_PLAYERS = 6;

    List<Player> players = new ArrayList<>();
    int[] places = new int[MAX_PLAYERS];
    int[] purses = new int[MAX_PLAYERS];
    boolean[] inPenaltyBox = new boolean[MAX_PLAYERS];

    LinkedList<String> popQuestions = new LinkedList<>();
    LinkedList<String> scienceQuestions = new LinkedList<>();
    LinkedList<String> sportsQuestions = new LinkedList<>();
    LinkedList<String> rockQuestions = new LinkedList<>();

    int currentPlayer = -1;
    boolean isGettingOutOfPenaltyBox;

    public Game() {
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast("Science Question " + i);
            sportsQuestions.addLast("Sports Question " + i);
            rockQuestions.addLast("Rock Question " + i);
        }
    }

    public void add(String playerName) {
        players.add(new Player(playerName));
        log("%s was added", playerName);
        log("They are player number %d", players.size());
    }

    public void roll(int roll) {
        log("They have rolled a %d", roll);
        if (inPenaltyBox[currentPlayer]) doIfInPenaltyBox(roll);
        else doIfOutOfPenaltyBox(roll);
    }

    public void advanceToNextPlayer() {
        currentPlayer++;
        currentPlayer %= players.size();
        log("%s is the current player", currentPlayer());
    }

    public void provideCorrectAnswer() {
        if (!isAllowedToAnswer()) return;
        purses[currentPlayer]++;
        log("Answer was correct!!!!");
        log("%s now has %d Gold Coins.", currentPlayer(), purses[currentPlayer]);
    }

    public void provideWrongAnswer() {
        log("Question was incorrectly answered");
        movePlayerToPenaltyBox();
    }

    private void movePlayerToPenaltyBox() {
        inPenaltyBox[currentPlayer] = true;
        log("%s was sent to the penalty box", currentPlayer());
    }

    public boolean isGameOver() {
        return purses[currentPlayer] == 6;
    }

    private boolean isAllowedToAnswer() {
        return (inPenaltyBox[currentPlayer] && isGettingOutOfPenaltyBox) ||
                !inPenaltyBox[currentPlayer];
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
        places[currentPlayer] %= 12;
    }

    private void doIfInPenaltyBox(int roll) {
        if (shouldMoveOutOfPenaltyBox(roll)) {
            moveOutOfPenaltyBox();
            doIfOutOfPenaltyBox(roll);
        } else keepInPenaltyBox();
    }

    private boolean shouldMoveOutOfPenaltyBox(int roll) {
        return roll % 2 != 0;
    }

    private void keepInPenaltyBox() {
        isGettingOutOfPenaltyBox = false;
        log("%s is not getting out of the penalty box", currentPlayer());
    }

    private void moveOutOfPenaltyBox() {
        isGettingOutOfPenaltyBox = true;
        log("%s is getting out of the penalty box", currentPlayer());
    }

    private void askQuestion() {
        String category = currentCategory();
        log("The category is %s", category);
        if ("Pop".equals(category))
            log(popQuestions.removeFirst());
        if ("Science".equals(category))
            log(scienceQuestions.removeFirst());
        if ("Sports".equals(category))
            log(sportsQuestions.removeFirst());
        if ("Rock".equals(category))
            log(rockQuestions.removeFirst());
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
