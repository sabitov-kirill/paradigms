package queue;

import java.util.Arrays;
import java.util.Objects;

/**
 * Abstract queue class.
 *
 * @custom.Model: a[0]..a[first]..a[first + n - 1] && first >= 0 && n >= 0.
 * @custom.Invariant: forall i in [first, first + n]: a[i] != null.
 */
public abstract class AbstractQueue implements Queue {
    protected int elementsCount = 0;

    protected abstract void enqueueImpl(Object element);
    protected abstract Object elementImpl();
    protected abstract Object dequeueImpl();
    protected abstract void clearImpl();

    /* Define immutable(a: int, b: int) -> boolean:
     *     forall i in [a, b]: a[i] = a'[i].
     */

    /**
     * Add element to queue function.
     *
     * @custom.Pred: element != null.
     * @custom.Post: immutable(first, first + n - 1) && n' == n + 1 && a[first + n'] == element
     */
    public void enqueue(Object element) {
        Objects.requireNonNull(element);

        enqueueImpl(element);
        elementsCount++;
    }

    /**
     * Get first element in queue function.
     *
     * @custom.Pred: n > 0.
     * @custom.Post: R == a[first] && immutable(first, first + n - 1).
     */
    public Object element() {
        assert elementsCount > 0;
        return elementImpl();
    }

    /**
     * Remove and return fist element in queue.
     *
     * @custom.Pred: n > 0.
     * @custom.Post: R == a[first] && first' == first + 1 && immutable(first', first + n - 1).
     */
    public Object dequeue() {
        assert elementsCount > 0;
        elementsCount--;
        return dequeueImpl();
    }

    /**
     * Delete all elements from queue function.
     *
     * @custom.Pred: true.
     * @custom.Post: n == 0.
     */
    public void clear() {
        elementsCount = 0;
        clearImpl();
    }

    /**
     * Queue string representation getter function.
     *
     * @custom.Pred: true.
     * @custom.Post: R == "[element[first], ..., element[first + n - 1]]" && immutable(first, last).
     */
    public String toStr() {
        StringBuilder sb = new StringBuilder("[");
        boolean appended = false;

        for (Object element: this) {
            sb.append(element).append(", ");
            appended = true;
        }
        if (appended) {
            sb.setLength(sb.length() - 2);
        }

        return sb.append("]").toString();
    }

    /**
     * Queue array representation getter function.
     *
     * @custom.Pred: true.
     * @custom.Post: R == [ element[first], ..., element[first + n - 1] ] && immutable(first, first + n - 1).
     */
    public Object[] toArray() {
        Object[] array = new Object[elementsCount];
        int iterations = 0;
        for (Object element: this) {
            array[iterations++] = element;
        }

        return array;
    }

    /**
     * Queue count elements function.
     *
     * @custom.Pred: true.
     * @custom.Post: R == count of elements that are equal to argument.
     */
    public int count(Object elementToCount) {
        int result = 0;
        for (Object element: this) {
            if (element.equals(elementToCount)) {
                result++;
            }
        }
        return result;
    }

    public int indexOf(Object elementToFind) {
        int index = 0;
        for (Object element: this) {
            if (element.equals(elementToFind)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public int lastIndexOf(Object elementToFind) {
        int index = 0, resultIndex = -1;
        for (Object element: this) {
            if (element.equals(elementToFind)) {
                resultIndex = index;
            }
            index++;
        }

        return resultIndex;
    }

    /**
     * Current queue size getter function.
     *
     * @custom.Pred: true.
     * @custom.Post: R == n && immutable(first, first + n - 1).
     */
    public int size() {
        return elementsCount;
    }

    /**
     * Check if queue is empty function.
     *
     * @custom.Pred: true.
     * @custom.Post: R == (n == 0) && immutable(first, first + n - 1).
     */
    public boolean isEmpty() {
        return elementsCount == 0;
    }
}
