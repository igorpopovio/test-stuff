package trivia;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Runner {
    private Game game;
    private Random random;

    public static Game createGame() {
        List<String> categories = Arrays.asList(
                "Pop", "Science", "Sports", "Rock");
        Game aGame = new Game(categories);
        aGame.add(new Player("Chet"));
        aGame.add(new Player("Pat"));
        aGame.add(new Player("Sue"));
        return aGame;
    }

    public Runner(Game game, Random random) {
        this.game = game;
        this.random = random;
    }

    public static void main(String[] args) {
        new Runner(createGame(), new Random()).run();
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
