package trivia;

import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

public class Runner {
    private Game game;
    private Random random;

    public static Game createGame() {
        List<String> categories = asList(
                "Pop",
                "Science",
                "Sports",
                "Rock");
        List<Player> players = asList(
                new Player("Chet"),
                new Player("Pat"),
                new Player("Sue"));
        return new Game(categories, players);
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
