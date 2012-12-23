package trivia;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameRunner {
    private Game game;
    private Random random;

    public static QuestionBoard createQuestionBoard() {
        List<String> categories = Arrays.asList(
                "Pop", "Science", "Sports", "Rock");
        GeneratedQuestionBoardFactory boardFactory =
                new GeneratedQuestionBoardFactory(categories, 50);
        return boardFactory.createBoard();
    }

    public static Game createGame() {
        Game aGame = new Game(createQuestionBoard());
        aGame.add(new Player("Chet"));
        aGame.add(new Player("Pat"));
        aGame.add(new Player("Sue"));
        return aGame;
    }

    public GameRunner(Game game, Random random) {
        this.game = game;
        this.random = random;
    }

    public static void main(String[] args) {
        new GameRunner(createGame(), new Random()).run();
    }

    public void run() {
        do {
            game.advanceToNextPlayer();
            game.roll(rollDie());
            if (game.isAllowedToAnswer())
                provideAnswer();
        } while (!game.isGameOver());
    }

    private void provideAnswer() {
        if (shouldAnswerCorrectly())
            game.provideCorrectAnswer();
        else
            game.provideWrongAnswer();
    }

    private boolean shouldAnswerCorrectly() {
        return random.nextInt(9) != 7;
    }

    private int rollDie() {
        return random.nextInt(5) + 1;
    }
}
