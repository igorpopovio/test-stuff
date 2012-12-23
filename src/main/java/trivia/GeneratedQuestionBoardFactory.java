package trivia;

import java.util.Iterator;
import java.util.List;

public class GeneratedQuestionBoardFactory implements QuestionBoardFactory {
    private List<String> categories;
    private int questionsPerCategory;

    public GeneratedQuestionBoardFactory(
            List<String> categories,
            int questionsPerCategory) {
        this.categories = categories;
        this.questionsPerCategory = questionsPerCategory;
    }

    @Override
    public QuestionBoard createBoard() {
        DefaultQuestionBoard board = new DefaultQuestionBoard();
        int timesToIterate = questionsPerCategory * categories.size();
        Iterator<String> categoryIterator = new CircularIterator<>(categories);
        for (int i = 0; i < timesToIterate; i++) {
            int questionIndex = i;
            for (int j = 0; j < categories.size(); j++) {
                String category = categoryIterator.next();
                board.addQuestion(createQuestion(questionIndex, category));
            }
        }
        return board;
    }

    private Question createQuestion(int index, String category) {
        return new Question(category, createQuestionText(index, category));
    }

    private String createQuestionText(int index, String category) {
        return String.format("%s Question %d", category, index);
    }
}
