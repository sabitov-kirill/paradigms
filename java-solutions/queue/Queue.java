package queue;

/**
 * Queue interface.
 *
 * @custom.Model: (a[0]..a[first]..a[first + n - 1] || n == 0) && first >= 0 && n >= 0.
 * @custom.Invariant: forall i in [first, first + n]: a[i] != null.
 */
public interface Queue extends Iterable<Object> {
    /* Define immutable(a: int, b: int) -> boolean:
     *     forall i in [a, b]: a[i] = a'[i].
     */

    /**
     * Add element to queue function.
     *
     * @custom.Pred: element != null.
     * @custom.Post: immutable(first, first + n - 1) && n' == n + 1 && a[first + n'] == element
     */
    void enqueue(Object element);

    /**
     * Get first element in queue function.
     *
     * @custom.Pred: n > 0.
     * @custom.Post: R == a[first] && immutable(first, first + n - 1).
     */
    Object element();

    /**
     * Remove and return fist element in queue.
     *
     * @custom.Pred: n > 0.
     * @custom.Post: R == a[first] && first' == first + 1 && immutable(first', first + n - 1).
     */
    Object dequeue();

    /**
     * Current queue size getter function.
     *
     * @custom.Pred: true.
     * @custom.Post: R == n && immutable(first, first + n - 1).
     */
    int size();

    /**
     * Check if queue is empty function.
     *
     * @custom.Pred: true.
     * @custom.Post: R == (n == 0) && immutable(first, first + n - 1).
     */
    boolean isEmpty();

    /**
     * Delete all elements from queue function.
     *
     * @custom.Pred: true.
     * @custom.Post: n == 0.
     */
    void clear();

    /**
     * Queue string representation getter function.
     *
     * @custom.Pred: true.
     * @custom.Post: R == "[element[first], ..., element[first + n - 1]]" && immutable(first, last).
     */
    String toStr();

    /**
     * Queue array representation getter function.
     *
     * @custom.Pred: true.
     * @custom.Post: R == [ element[first], ..., element[first + n - 1] ] && immutable(first, first + n - 1).
     */
    Object[] toArray();

    /**
     * Queue count elements function.
     *
     * @custom.Pred: true.
     * @custom.Post: R == count of elements that are equal to argument.
     */
    int count(Object element);

    /**
     * Queue element index getter function (first occurrence)  function.
     *
     * @custom.Pred: true.
     * @custom.Post: R == (index of first occurrence || -1 && element is not in queue).
     */
    int indexOf(Object element);

    /**
     * Queue element index getter function (last occurrence)  function.
     *
     * @custom.Pred: true.
     * @custom.Post: R == (index of last occurrence || -1 && element is not in queue).
     */
    int lastIndexOf(Object element);
}
