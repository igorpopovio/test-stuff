package trivia;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

// add 50 questions for each category
public class QuestionBoardFactoryTests {
    private static final String CATEGORY1 = "Category1";
    private static final String CATEGORY2 = "Category2";
    private QuestionsProvider board;

    @Before
    public void setUp() throws Exception {
        List<String> categories = asList(CATEGORY1, CATEGORY2);
        board = new GeneratedQuestionFactory().createQuestions(categories, 4);
    }

    @Test
    public void creationOfBoard() throws Exception {
        assertEquals("Category1 Question 0", board.getNextQuestionFor(CATEGORY1).getText());
        assertEquals("Category2 Question 0", board.getNextQuestionFor(CATEGORY2).getText());
        assertEquals("Category1 Question 1", board.getNextQuestionFor(CATEGORY1).getText());
        assertEquals("Category2 Question 1", board.getNextQuestionFor(CATEGORY2).getText());
    }
}
