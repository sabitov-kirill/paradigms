package queue;

import java.util.Iterator;
import java.util.Objects;

/**
 * Linked list based queue implementation class.
 *
 * @custom.Model: (a[0]..a[first]..a[first + n - 1] || n == 0) && first >= 0 && n >= 0.
 * @custom.Invariant: forall i in [first, first + n]: a[i] != null.
 */
public class LinkedQueue extends AbstractQueue {
    private static class Node {
        public Object element;
        public Node right;

        public Node(Object element, Node right) {
            this.element = element;
            this.right = right;
        }
    }

    private Node first = null, last = null;

    @Override
    protected void enqueueImpl(Object element) {
        Node newNode = new Node(element, null);
        if (first == null) {
            first = last = newNode;
        } else {
            last.right = newNode;
            last = newNode;
        }
    }

    @Override
    protected Object elementImpl() {
        return first.element;
    }

    @Override
    protected Object dequeueImpl() {
        Node prevFirst = first;
        first = first.right;
        if (first == null) {
            last = null;
        }
        return prevFirst.element;
    }

    @Override
    protected void clearImpl() {
        first = last = null;
    }

    private class QueueIterator implements Iterator<Object> {
        private Node currentIterationNode = first;

        @Override
        public boolean hasNext() {
            return currentIterationNode != null;
        }

        @Override
        public Object next() {
            Objects.requireNonNull(currentIterationNode);

            Node result = currentIterationNode;
            currentIterationNode = currentIterationNode.right;
            return result.element;
        }
    }

    public Iterator<Object> iterator() {
        return new QueueIterator();
    }
}
