package trivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
    public static final int MAX_PLAYERS = 6;

    List<String> players = new ArrayList<>();
    int[] places = new int[MAX_PLAYERS];
    int[] purses = new int[MAX_PLAYERS];
    boolean[] inPenaltyBox = new boolean[MAX_PLAYERS];

    LinkedList<String> popQuestions = new LinkedList<>();
    LinkedList<String> scienceQuestions = new LinkedList<>();
    LinkedList<String> sportsQuestions = new LinkedList<>();
    LinkedList<String> rockQuestions = new LinkedList<>();

    int currentPlayer = 0;
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
        players.add(playerName);

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
    }

    public void roll(int roll) {
        System.out.println(players.get(currentPlayer) + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (inPenaltyBox[currentPlayer]) doIfCurrentPlayerIsInPenaltyBox(roll);
        else doIfCurrentPlayerIsNotInPenaltyBox(roll);
    }

    private void doIfCurrentPlayerIsNotInPenaltyBox(int roll) {
        updatePlaceForCurrentPlayer(roll);

        System.out.println(players.get(currentPlayer)
                + "'s new location is "
                + places[currentPlayer]);
        System.out.println("The category is " + currentCategory());
        askQuestion();
    }

    private void updatePlaceForCurrentPlayer(int roll) {
        places[currentPlayer] = places[currentPlayer] + roll;
        if (places[currentPlayer] > 11) places[currentPlayer] -= 12;
    }

    private void doIfCurrentPlayerIsInPenaltyBox(int roll) {
        if (shouldMoveCurrentPlayerOutOfPenaltyBox(roll)) {
            moveCurrentPlayerOutOfPenaltyBox();
            doIfCurrentPlayerIsNotInPenaltyBox(roll);
        } else keepCurrentPlayerInPenaltyBox();
    }

    private boolean shouldMoveCurrentPlayerOutOfPenaltyBox(int roll) {
        return roll % 2 != 0;
    }

    private void keepCurrentPlayerInPenaltyBox() {
        isGettingOutOfPenaltyBox = false;
        System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
    }

    private void moveCurrentPlayerOutOfPenaltyBox() {
        isGettingOutOfPenaltyBox = true;
        System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
    }

    private void askQuestion() {
        if (currentCategory() == "Pop")
            System.out.println(popQuestions.removeFirst());
        if (currentCategory() == "Science")
            System.out.println(scienceQuestions.removeFirst());
        if (currentCategory() == "Sports")
            System.out.println(sportsQuestions.removeFirst());
        if (currentCategory() == "Rock")
            System.out.println(rockQuestions.removeFirst());
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

    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox[currentPlayer] && isGettingOutOfPenaltyBox) {
            correctAnswer();
            boolean winner = didCurrentPlayerWin();
            advanceToNextPlayer();
            return winner;
        }
        if (!inPenaltyBox[currentPlayer]) {
            correctAnswer();
        }
        boolean winner = didCurrentPlayerWin();
        advanceToNextPlayer();
        return winner;
    }

    private void correctAnswer() {
        System.out.println("Answer was correct!!!!");
        purses[currentPlayer]++;
        System.out.println(players.get(currentPlayer)
                + " now has "
                + purses[currentPlayer]
                + " Gold Coins.");
    }

    private void advanceToNextPlayer() {
        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
    }

    private boolean didCurrentPlayerWin() {
        return purses[currentPlayer] == 6;
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;
        boolean winner = didCurrentPlayerWin();
        advanceToNextPlayer();
        return winner;
    }
}
