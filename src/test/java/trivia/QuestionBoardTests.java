package trivia;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static trivia.QuestionBoard.NoQuestionAvailableException;

public class QuestionBoardTests {
    private static final String DEFAULT_CATEGORY = "category";
    private Question question1;
    private Question question2;

    private Question createQuestion(String text) {
        return new Question(DEFAULT_CATEGORY, text);
    }

    @Before
    public void setUp() throws Exception {
        question1 = createQuestion("question 1");
        question2 = createQuestion("question 2");
    }

    @Test
    public void boardWithSingleQuestion() throws Exception {
        QuestionBoard board = new QuestionBoard();
        board.addQuestion(question1);
        assertEquals(question1, board.getNextQuestionFor(DEFAULT_CATEGORY));
    }

    @Test
    public void boardWithTwoQuestions() throws Exception {
        QuestionBoard board = new QuestionBoard();
        board.addQuestion(question1);
        board.addQuestion(question2);
        assertEquals(question1, board.getNextQuestionFor(DEFAULT_CATEGORY));
        assertEquals(question2, board.getNextQuestionFor(DEFAULT_CATEGORY));
    }

    @Test(expected = NoQuestionAvailableException.class)
    public void extractingQuestionsWhenBoardIsEmpty() throws Exception {
        new QuestionBoard().getNextQuestionFor(DEFAULT_CATEGORY);
    }
}
