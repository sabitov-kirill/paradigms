package queue;

import java.util.Arrays;
import java.util.Objects;

/**
 * Array queue implementation module.
 *
 * @custom.Model: (a[0]..a[first]..a[first + n - 1] || n == 0) && first >= 0 && n >= 0.
 * @custom.Invariant: forall i in [first, first + n]: a[i] != null.
 */
public class ArrayQueueModule {
    public static final int DEFAULT_CAPACITY = 8;
    private static Object[] elements = new Object[DEFAULT_CAPACITY];
    private static int first = 0, elementsCount = 0;

    /* Define immutable(a: int, b: int) -> boolean:
     *     forall i in [a, b]: a[i] = a'[i].
     */

    /**
     * Add element to queue function.
     *
     * @custom.Pred: element != null.
     * @custom.Post: immutable(first, first + n - 1) && n' == n + 1 && a[first + n'] == element
     */
    public static void enqueue(Object element) {
        Objects.requireNonNull(element);

        ensureCapacity(elementsCount + 1);
        elements[(first + elementsCount) % elements.length] = element;
        elementsCount++;
    }

    /**
     * Get first element in queue function.
     *
     * @custom.Pred: n > 0.
     * @custom.Post: R == a[first] && immutable(first, first + n - 1).
     */
    public static Object element() {
        assert elementsCount > 0;
        return elements[first];
    }


    /**
     * Remove and return fist element in queue.
     *
     * @custom.Pred: n > 0.
     * @custom.Post: R == a[first] && first' == first + 1 && immutable(first', first + n - 1).
     */
    public static Object dequeue() {
        assert elementsCount > 0;
        Object result = elements[first];
        first = (first + 1 + elements.length) % elements.length;
        elementsCount--;

        return result;
    }

    /**
     * Current queue size getter function.
     *
     * @custom.Pred: true.
     * @custom.Post: R == n && immutable(first, first + n - 1).
     */
    public static int size() {
        return elementsCount;
    }

    /**
     * Check if queue is empty function.
     *
     * @custom.Pred: true.
     * @custom.Post: R == (n == 0) && immutable(first, first + n - 1).
     */
    public static boolean isEmpty() {
        return elementsCount == 0;
    }

    /**
     * Delete all elements from queue function.
     *
     * @custom.Pred: true.
     * @custom.Post: n == 0.
     */
    public static void clear() {
        first = 0;
        elementsCount = 0;

        // Arrays.fill(elements, null);
        elements = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Queue string representation getter function.
     *
     * @custom.Pred: true.
     * @custom.Post: R == "[element[first], ..., element[first + n - 1]]" && immutable(first, last).
     */
    public static String toStr() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < elementsCount; i++) {
            sb.append(elements[(i + first) % elements.length]);
            if (i != elementsCount - 1) {
                sb.append(", ");
            }
        }
        return sb.append("]").toString();
    }

    /**
     * Queue array representation getter function.
     *
     * @custom.Pred: true.
     * @custom.Post: R == [ element[first], ..., element[first + n - 1] ] && immutable(first, first + n - 1).
     */
    public static Object[] toArray() {
        Object[] array = new Object[elementsCount];
        for (int i = 0; i < elementsCount; i++) {
            array[i] = elements[(i + first) % elements.length];
        }
        return array;
    }

    private static void ensureCapacity(int expectedCapacity) {
        if (expectedCapacity <= elements.length) {
            return;
        }

        elements = Arrays.copyOf(toArray(), expectedCapacity * 2);
        first = 0;
    }
}
