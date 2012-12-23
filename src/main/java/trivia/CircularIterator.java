package trivia;

import java.util.Iterator;
import java.util.List;

public class CircularIterator<T> implements Iterator<T> {
    private List<T> list;
    private Iterator<T> iterator;

    public CircularIterator(List<T> list) {
        this.list = list;
        this.iterator = list.iterator();
    }

    @Override
    public boolean hasNext() {
        return !list.isEmpty();
    }

    @Override
    public T next() {
        if (!iterator.hasNext())
            iterator = list.iterator();
        return iterator.next();
    }

    @Override
    public void remove() {
    }
}
