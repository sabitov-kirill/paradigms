package queue;

import java.util.Objects;

/**
 * Array queue implementation module.
 *
 * @custom.Model: a[0]..a[first]..a[last] && first >= 0.
 * @custom.Invariant: forall i in [first, last]: a[i] != null
 */
public class ArrayQueueADT {
    private static final int DEFAULT_CAPACITY = 8;
    private Object[] elements = new Object[DEFAULT_CAPACITY];
    private int last = 0, first = 0, elementsCount = 0;

    /* Define immutable(a: int, b: int) -> boolean:
     *     forall i in [a, b]: a[i] = a'[i].
     */

    public static ArrayQueueADT create() {
        return new ArrayQueueADT();
    }

    /**
     * Add element to queue function.
     *
     * @custom.Pred: element != null.
     * @custom.Post: immutable(first, last) && last' = last + 1 && a[last'] == element
     */
    public static void enqueue(ArrayQueueADT queue, Object element) {
        Objects.requireNonNull(element);

        ensureCapacity(queue, queue.elementsCount + 1);
        queue.elements[queue.last] = element;
        queue.last = (queue.last + 1) % queue.elements.length;
        queue.elementsCount++;
    }

    /**
     * Get first element in queue function.
     *
     * @custom.Pred: first != last.
     * @custom.Post: R == a[first] && immutable(first, last).
     */
    public static Object element(ArrayQueueADT queue) {
        assert queue.elementsCount > 0;
        return queue.elements[queue.first];
    }

    /**
     * Remove and return fist element in queue.
     *
     * @custom.Pred: first != last.
     * @custom.Post: R == a[first] && first' = first + 1 && immutable(first', last).
     */
    public static Object dequeue(ArrayQueueADT queue) {
        assert queue.elementsCount > 0;
        Object result = queue.elements[queue.first];
        queue.first = (queue.first + 1 + queue.elements.length) % queue.elements.length;
        queue.elementsCount--;

        return result;
    }

    /**
     * Current queue size getter function.
     *
     * @custom.Pred: true.
     * @custom.Post: R == last - first + 1 && immutable(first, last).
     */
    public static int size(ArrayQueueADT queue) {
        return queue.elementsCount;
    }

    /**
     * Check if queue is empty function.
     *
     * @custom.Pred: true.
     * @custom.Post: R == (last == first) && immutable(first, last).
     */
    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.elementsCount == 0;
    }

    /**
     * Delete all elements from queue function.
     *
     * @custom.Pred: true.
     * @custom.Post: first == last == 0.
     */
    public static void clear(ArrayQueueADT queue) {
        queue.first = queue.last = 0;
        queue.elementsCount = 0;

        // Arrays.fill(queue.elements, null);
        queue.elements = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Queue string representation getter function.
     *
     * @custom.Pred: true.
     * @custom.Post: R == "[element[first], ..., element[last]]".
     */
    public static String toStr(ArrayQueueADT queue) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < queue.elementsCount; i++) {
            sb.append(queue.elements[(i + queue.first) % queue.elements.length]);
            if (i != queue.elementsCount - 1) {
                sb.append(", ");
            }
        }

        return sb.append("]").toString();
    }

    /**
     * Queue array representation getter function.
     *
     * @custom.Pred: true.
     * @custom.Post: R == { [element[first], ..., element[last]] }.
     */
    public static Object[] toArray(ArrayQueueADT queue) {
        Object[] array = new Object[queue.elementsCount];
        for (int i = 0; i < queue.elementsCount; i++) {
            array[i] = queue.elements[(i + queue.first) % queue.elements.length];
        }
        return array;
    }

    private static void ensureCapacity(ArrayQueueADT queue, int expectedCapacity) {
        if (expectedCapacity <= queue.elements.length) {
            return;
        }

        Object[] resizedElements = new Object[expectedCapacity * 2];
        System.arraycopy(queue.elements, queue.first, resizedElements, 0, queue.elements.length - queue.first);
        System.arraycopy(queue.elements, 0, resizedElements, queue.elements.length - queue.first, queue.last);
        queue.elements = resizedElements;

        queue.first = 0;
        queue.last = queue.elementsCount;
    }
}
