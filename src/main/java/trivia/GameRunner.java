package trivia;

import java.util.Random;

public class GameRunner {
    private Game game;
    private Random random;

    public static Game createGame() {
        Game aGame = new Game();
        aGame.add("Chet");
        aGame.add("Pat");
        aGame.add("Sue");
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
            game.roll(random.nextInt(5) + 1);
            if (random.nextInt(9) != 7)
                game.wasCorrectlyAnswered();
            else
                game.wrongAnswer();
        } while (!game.isGameOver());
    }
}
