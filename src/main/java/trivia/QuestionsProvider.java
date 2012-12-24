package trivia;

import com.google.common.collect.LinkedListMultimap;

import java.util.ArrayList;
import java.util.List;

public class QuestionsProvider {
    private LinkedListMultimap<String, Question> questions;

    public QuestionsProvider() {
        questions = LinkedListMultimap.create();
    }

    public void addQuestion(Question question) {
        this.questions.put(question.getCategory(), question);
    }

    public Question getNextQuestionFor(String category) {
        List<Question> questionsInCategory = questions.get(category);
        if (questionsInCategory.isEmpty())
            throw new NoQuestionAvailableException();
        Question question = questionsInCategory.get(0);
        questions.remove(category, question);
        return question;
    }

    public List<String> getCategories() {
        return new ArrayList<>(questions.keySet());
    }

    public static class NoQuestionAvailableException
            extends RuntimeException {
    }
}
