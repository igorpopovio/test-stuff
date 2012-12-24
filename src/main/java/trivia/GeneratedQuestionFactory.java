package trivia;

import java.util.Iterator;
import java.util.List;

public class GeneratedQuestionFactory {
    public QuestionsProvider createQuestions(
            List<String> categories, int questionsPerCategory) {
        QuestionsProvider board = new QuestionsProvider();
        int timesToIterate = questionsPerCategory * categories.size();
        Iterator<String> categoryIterator = new RingIterator<>(categories);
        for (int i = 0; i < timesToIterate; i++)
            for (int j = 0; j < categories.size(); j++)
                board.addQuestion(createQuestion(i, categoryIterator.next()));
        return board;
    }

    private Question createQuestion(int index, String category) {
        return new Question(category, createQuestionText(index, category));
    }

    private String createQuestionText(int index, String category) {
        return String.format("%s Question %d", category, index);
    }
}
