package trivia;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

public class CircularIteratorTests {
    private CircularIterator makeCircularIteratorFor(List<?> list) {
        return new CircularIterator(list);
    }

    @Test
    public void emptyListDoesNotHaveNextElements() throws Exception {
        Iterator iterator = makeCircularIteratorFor(asList());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void listWithSingleElementHasNext() throws Exception {
        assertTrue(makeCircularIteratorFor(asList(1)).hasNext());
    }

    @Test
    public void listWithSingleElementGivesSameElementOverAndOverAgain() throws Exception {
        List<Integer> list = asList(1);
        CircularIterator iterator = makeCircularIteratorFor(list);
        assertEquals(1, iterator.next());
        assertEquals(1, iterator.next());
        assertEquals(1, iterator.next());
    }

    @Test
    public void listWithTwoElementsGivesSameElementsOverAndOverAgain() throws Exception {
        List<Integer> list = asList(2, 3);
        CircularIterator iterator = makeCircularIteratorFor(list);
        assertEquals(2, iterator.next());
        assertEquals(3, iterator.next());
        assertEquals(2, iterator.next());
        assertEquals(3, iterator.next());
    }
}
