package queue;

import java.util.Objects;

/**
 * Array queue implementation module.
 *
 * @custom.Model: a[0]..a[first]..a[last] && first >= 0.
 * @custom.Invariant: forall i in [first, last]: a[i] != null
 */
public class ArrayQueueModule {
    public static final int DEFAULT_CAPACITY = 8;
    private static Object[] elements = new Object[DEFAULT_CAPACITY];
    private static int last = 0, first = 0, elementsCount = 0;

    /* Define immutable(a: int, b: int) -> boolean:
     *     forall i in [a, b]: a[i] = a'[i].
     */

    /**
     * Add element to queue function.
     *
     * @custom.Pred: element != null.
     * @custom.Post: immutable(first, last) && last' = last + 1 && a[last'] == element
     */
    public static void enqueue(Object element) {
        Objects.requireNonNull(element);

        ensureCapacity(elementsCount + 1);
        elements[last] = element;
        last = (last + 1) % elements.length;
        elementsCount++;
    }

    /**
     * Get first element in queue function.
     *
     * @custom.Pred: first != last.
     * @custom.Post: R == a[first] && immutable(first, last).
     */
    public static Object element() {
        assert elementsCount > 0;
        return elements[first];
    }

    /**
     * Remove and return fist element in queue.
     *
     * @custom.Pred: first != last.
     * @custom.Post: R == a[first] && first' = first + 1 && immutable(first', last).
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
     * @custom.Post: R == last - first + 1 && immutable(first, last).
     */
    public static int size() {
        return elementsCount;
    }

    /**
     * Check if queue is empty function.
     *
     * @custom.Pred: true.
     * @custom.Post: R == (last == first) && immutable(first, last).
     */
    public static boolean isEmpty() {
        return elementsCount == 0;
    }

    /**
     * Delete all elements from queue function.
     *
     * @custom.Pred: true.
     * @custom.Post: first == last == 0.
     */
    public static void clear() {
        first = last = 0;
        elementsCount = 0;

        // Arrays.fill(elements, null);
        elements = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Queue string representation getter function.
     *
     * @custom.Pred: true.
     * @custom.Post: R == "[element[first], ..., element[last]]" && immutable(first, last).
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
     * @custom.Post: R == { [element[first], ..., element[last]] } && immutable(first, last).
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

        Object[] resizedElements = new Object[expectedCapacity * 2];
        System.arraycopy(elements, first, resizedElements, 0, elements.length - first);
        System.arraycopy(elements, 0, resizedElements, elements.length - first, last);
        elements = resizedElements;

        first = 0;
        last = elementsCount;
    }
}
