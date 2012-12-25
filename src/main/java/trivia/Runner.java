package trivia;

import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

public class Runner {
    private Game game;
    private Random random;

    public static Game createGame(Random random) {
        List<String> categories = asList(
                "Pop",
                "Science",
                "Sports",
                "Rock");
        List<Player> players = asList(
                new Player("Chet", random),
                new Player("Pat", random),
                new Player("Sue", random));
        return new Game(categories, players);
    }

    public Runner(Game game, Random random) {
        this.game = game;
        this.random = random;
    }

    public static void main(String[] args) {
        Random random = new Random();
        new Runner(createGame(random), random).run();
    }

    public void run() {
        do {
            Player player = game.nextPlayer();
            game.roll(rollDie());
            if (player.canAnswer())
                player.provideAnswer();
        } while (!game.isGameOver());
    }

    private int rollDie() {
        return random.nextInt(5) + 1;
    }
}
