package trivia;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

public class RingIteratorTests {
    private <T> RingIterator<T> createRingIteratorFor(List<T> list) {
        return new RingIterator<>(list);
    }

    @Test
    public void emptyListDoesNotHaveNextElements() throws Exception {
        Iterator iterator = createRingIteratorFor(asList());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void listWithSingleElementHasNext() throws Exception {
        assertTrue(createRingIteratorFor(asList(1)).hasNext());
    }

    @Test
    public void listWithSingleElementGivesSameElementOverAndOverAgain() throws Exception {
        List<Integer> list = asList(1);
        RingIterator iterator = createRingIteratorFor(list);
        assertEquals(1, iterator.next());
        assertEquals(1, iterator.next());
        assertEquals(1, iterator.next());
    }

    @Test
    public void listWithTwoElementsGivesSameElementsOverAndOverAgain() throws Exception {
        List<Integer> list = asList(2, 3);
        RingIterator iterator = createRingIteratorFor(list);
        assertEquals(2, iterator.next());
        assertEquals(3, iterator.next());
        assertEquals(2, iterator.next());
        assertEquals(3, iterator.next());
    }
}
