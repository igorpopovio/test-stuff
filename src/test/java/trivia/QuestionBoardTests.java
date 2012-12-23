package trivia;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static trivia.QuestionBoard.NoQuestionAvailableException;

public class QuestionBoardTests {
    @Test
    public void boardWithSingleQuestion() throws Exception {
        QuestionBoard board = new QuestionBoard();
        board.addQuestions("category", "Question");
        assertEquals("Question", board.getNextQuestionForCategory("category"));
    }

    @Test
    public void boardWithTwoQuestions() throws Exception {
        QuestionBoard board = new QuestionBoard();
        board.addQuestions("category", "Question 1", "Question 2");
        assertEquals("Question 1", board.getNextQuestionForCategory("category"));
        assertEquals("Question 2", board.getNextQuestionForCategory("category"));
    }

    @Test(expected = NoQuestionAvailableException.class)
    public void extractingQuestionsWhenBoardIsEmpty() throws Exception {
        new QuestionBoard().getNextQuestionForCategory("category");
    }
}
