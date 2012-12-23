package trivia;

import com.google.common.collect.LinkedListMultimap;

import java.util.List;

import static java.util.Arrays.asList;

public class QuestionBoard {
    private LinkedListMultimap<String, String> questions;

    public QuestionBoard() {
        questions = LinkedListMultimap.create();
    }

    public String getNextQuestionForCategory(String category) {
        List<String> questionsInCategory = questions.get(category);
        if (questionsInCategory.isEmpty())
            throw new NoQuestionAvailableException();
        String question = questionsInCategory.get(0);
        questions.remove(category, question);
        return question;
    }

    public void addQuestions(String category, String... questions) {
        this.questions.putAll(category, asList(questions));
    }

    public static class NoQuestionAvailableException
            extends RuntimeException {
    }
}
