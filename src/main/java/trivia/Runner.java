package trivia;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;
import static trivia.Logger.log;

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
        logAddedPlayers(players);
        return new Game(categories, players, random);
    }

    public static void main(String[] args) {
        createGame(new Random()).run();
    }

    private static void logAddedPlayers(List<Player> players) {
        for (int i = 0; i < players.size(); i++) {
            log("%s was added", players.get(i));
            log("They are player number %d", i + 1);
        }
    }
}
