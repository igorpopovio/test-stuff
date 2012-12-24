package trivia;

import java.util.*;

public class Board {
    public static final int MAX_NUMBER_OF_PLACES = 12;
    public static final int QUESTIONS_PER_CATEGORY = 50;

    private List<String> categories;
    private QuestionsProvider questionsProvider;

    private List<String> placeCategories;
    private Map<Player, Integer> playerPlaces;

    public Board(List<String> categories, List<Player> players) {
        this.categories = categories;
        questionsProvider = createQuestionProvider();
        initializePlaceCategories();
        initializePlacesFor(players);
    }

    private void initializePlacesFor(List<Player> players) {
        playerPlaces = new HashMap<>();
        for (Player player : players)
            playerPlaces.put(player, 0);
    }

    private void initializePlaceCategories() {
        placeCategories = new ArrayList<>();
        Iterator<String> iterator = new RingIterator<>(categories);
        for (int i = 0; i < MAX_NUMBER_OF_PLACES; i++)
            placeCategories.add(iterator.next());
    }

    private QuestionsProvider createQuestionProvider() {
        GeneratedQuestionFactory factory = new GeneratedQuestionFactory();
        return factory.createQuestions(categories, QUESTIONS_PER_CATEGORY);
    }

    public void updatePlace(Player player, int roll) {
        int currentPlace = playerPlaces.get(player);
        currentPlace += roll;
        currentPlace %= MAX_NUMBER_OF_PLACES;
        playerPlaces.put(player, currentPlace);
    }

    public Question provideQuestionFor(Player player) {
        String category = getCurrentCategoryFor(player);
        return questionsProvider.getNextQuestionFor(category);
    }

    public String getCurrentCategoryFor(Player player) {
        return placeCategories.get(getCurrentPlaceFor(player));
    }

    public int getCurrentPlaceFor(Player player) {
        return playerPlaces.get(player);
    }
}
