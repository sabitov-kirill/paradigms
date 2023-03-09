package queue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * Array queue implementation class.
 *
 * @custom.Model: (a[0]..a[first]..a[first + n - 1] || n == 0) && first >= 0 && n >= 0.
 * @custom.Invariant: forall i in [first, first + n]: a[i] != null.
 */
public class ArrayQueue extends AbstractQueue {
    public static final int DEFAULT_CAPACITY = 8;
    private Object[] elements = new Object[DEFAULT_CAPACITY];
    private int first = 0;

    @Override
    protected void enqueueImpl(Object element) {
        ensureCapacity(elementsCount + 1);
        elements[(first + elementsCount) % elements.length] = element;
    }

    @Override
    protected Object elementImpl() {
        assert elementsCount > 0;
        return elements[first];
    }

    @Override
    protected Object dequeueImpl() {
        Object result = elements[first];
        first = (first + 1 + elements.length) % elements.length;
        return result;
    }

    @Override
    protected void clearImpl() {
        first = 0;
        elements = new Object[DEFAULT_CAPACITY];
    }

    private class QueueIterator implements Iterator<Object> {
        private int iterationIndex = 0;

        @Override
        public boolean hasNext() {
            return iterationIndex < elementsCount && elementsCount != 0;
        }

        @Override
        public Object next() {
            return elements[(first + iterationIndex++) % elements.length];
        }
    }

    @Override
    public Iterator<Object> iterator() {
        return new QueueIterator();
    }

    private void ensureCapacity(int expectedCapacity) {
        if (expectedCapacity <= elements.length) {
            return;
        }

        elements = Arrays.copyOf(toArray(), expectedCapacity * 2);
        first = 0;
    }
}
