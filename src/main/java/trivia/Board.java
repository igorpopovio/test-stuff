package trivia;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static trivia.Logger.log;


/* classes I should have:
*
* Game
* Runner
* Logger
* Board
* Player
* Question
* QuestionProvider ??????? should be some kind of collection that provides questions
* on demand in some specific order and also removes them when returning the questions.
*
*
*
*
* I hate interfaces and factories ---> they make code extremely hard to change
* (you need to change it in too many places). Maybe they should be created in the
* end when you already separated responsibilities in different classes.
* */


public class Board {
    public static final int MAX_NUMBER_OF_PLAYERS = 6;
    public static final int MAX_NUMBER_OF_PLACES = 12;
    public static final int QUESTIONS_PER_CATEGORY = 50;

    List<Player> players = new ArrayList<>();
    int currentPlayer = -1;

    private List<String> categories;
    private QuestionsProvider questionsProvider;

    int[] places = new int[MAX_NUMBER_OF_PLAYERS];
    List<String> placeCategories;

    public Board(List<String> categories) {
        this.categories = categories;
        questionsProvider = createQuestionProvider();
        initializePlaceCategories();
    }

    private void initializePlaceCategories() {
        placeCategories = new ArrayList<>();
        Iterator<String> iterator = new CircularIterator<>(categories);
        for (int i = 0; i < MAX_NUMBER_OF_PLACES; i++)
            placeCategories.add(iterator.next());
    }

    private QuestionsProvider createQuestionProvider() {
        GeneratedQuestionFactory factory = new GeneratedQuestionFactory();
        return factory.createQuestions(categories, QUESTIONS_PER_CATEGORY);
    }

    private void updatePlace(int roll) {
        places[currentPlayer] += roll;
        places[currentPlayer] %= MAX_NUMBER_OF_PLACES;
    }

    private void askQuestion() {
        String category = currentCategory();
        log("The category is %s", category);
        Question question = questionsProvider.getNextQuestionFor(category);
        log(question.getText());
    }

    private String currentCategory() {
        return placeCategories.get(places[currentPlayer]);
    }
}
