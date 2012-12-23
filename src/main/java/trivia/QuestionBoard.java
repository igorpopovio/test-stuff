package trivia;

import java.util.List;

public interface QuestionBoard {
    void addQuestion(Question question);

    Question getNextQuestionFor(String category);

    List<String> getCategories();
}
