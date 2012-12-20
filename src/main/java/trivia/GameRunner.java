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
            game.roll(rollDie());
            if (shouldAnswerCorrectly())
                game.wasCorrectlyAnswered();
            else
                game.wrongAnswer();
        } while (!game.isGameOver());
    }

    private boolean shouldAnswerCorrectly() {
        return random.nextInt(9) != 7;
    }

    private int rollDie() {
        return random.nextInt(5) + 1;
    }
}
