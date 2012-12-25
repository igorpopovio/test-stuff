package trivia;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

public class Runner {
    public static Game createGame(Random random) {
        List<String> categories = asList(
                "Pop",
                "Science",
                "Sports",
                "Rock");
        List<Player> players = new ArrayList<>();
        players.add(new AiPlayer("Chet", random));
        players.add(new AiPlayer("Pat", random));
        players.add(new AiPlayer("Sue", random));
        return new Game(categories, players, random);
    }

    public static void main(String[] args) {
        createGame(new Random()).run();
    }
}
