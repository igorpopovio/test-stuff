package trivia;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static trivia.QuestionsProvider.NoQuestionAvailableException;

public class QuestionBoardTests {
    private static final String DEFAULT_CATEGORY = "category";
    private Question question1;
    private Question question2;
    private QuestionsProvider board;

    private Question createQuestion(String text) {
        return new Question(DEFAULT_CATEGORY, text);
    }

    @Before
    public void setUp() throws Exception {
        question1 = createQuestion("question 1");
        question2 = createQuestion("question 2");
        board = new QuestionsProvider();
    }

    @Test
    public void boardWithSingleQuestion() throws Exception {
        board.addQuestion(question1);
        assertEquals(question1, board.getNextQuestionFor(DEFAULT_CATEGORY));
    }

    @Test
    public void boardWithTwoQuestions() throws Exception {
        board.addQuestion(question1);
        board.addQuestion(question2);
        assertEquals(question1, board.getNextQuestionFor(DEFAULT_CATEGORY));
        assertEquals(question2, board.getNextQuestionFor(DEFAULT_CATEGORY));
    }

    @Test(expected = NoQuestionAvailableException.class)
    public void extractingQuestionsWhenBoardIsEmpty() throws Exception {
        board.getNextQuestionFor(DEFAULT_CATEGORY);
    }

    @Test
    public void retrievingAllCategories() throws Exception {
        board.addQuestion(new Question("c1", "not.important"));
        board.addQuestion(new Question("c2", "not.important"));
        board.addQuestion(new Question("c3", "not.important"));
        board.addQuestion(new Question("c3", "not.important"));
        assertEquals(Arrays.asList("c1", "c2", "c3"), board.getCategories());
    }

}
