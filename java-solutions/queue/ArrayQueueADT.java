package queue;

import java.util.Objects;

/*
a - synonym of 'elements'
Model: a[0] .. a[cap - 1], cap - capacity of queue
Invariant: forall head <= i <= head + size - 1: a[i] != null;  0<= size <= cap,
            0 <= head < capacity, next(index) = (index + 1) % cap
Immutable(queue, head, size) : forall i, head <= i <= head + size - 1: queue.a[i] == queue.a'[i]
 */
public class ArrayQueueADT {
    private static final int INITIAL_CAPACITY = 5;
    private int size = 0;
    private int head = 0;
    private Object[] elements = new Object[INITIAL_CAPACITY];
    private int cap = elements.length;

    /* adds element to queue
    Pred: queueADT != null && element != null
    Post: queueADT.size' == queueADT.size + 1 && Immutable(queueADT, head, size)
           && queueADT.a[queueADT.size] == element
    */
    public static void enqueue(ArrayQueueADT queueADT, final Object element) {
        Objects.requireNonNull(queueADT);
        Objects.requireNonNull(element);
        ensureCapacity(queueADT, queueADT.size + 1);
        queueADT.elements[(queueADT.head + queueADT.size++) % queueADT.cap] = element;
    }

    /*
    first element in queue
    Pred: queueADT != null && queueADT.size >= 1
    Post: Immutable(queueADT, head, size) && size == size' && R == queueADT.a[queueADT.head]
     */
    public static Object element(final ArrayQueueADT queueADT) {
        assert Objects.requireNonNull(queueADT).size >= 1;
        return queueADT.elements[queueADT.head];
    }

    /*
    deletes and returns first element in queue
    Pred: queueADT != null && queueADT.size >= 1
    Post: Immutable(queueADT, next(head), size) && size' == size - 1 && R == queueADT.a[queueADT.head]
            && head' == next(head)
     */
    public static Object dequeue(ArrayQueueADT queueADT) {
        assert Objects.requireNonNull(queueADT).size >= 1;
        Object tmp = queueADT.elements[queueADT.head];
        queueADT.elements[queueADT.head] = null;
        queueADT.head = next(queueADT, queueADT.head);
        queueADT.size--;
        return tmp;
    }

    /*
    queue size
    Pred: queueADT != null
    Post: R == size == size' && Immutable(queueADT, head, size)
     */
    public static int size(final ArrayQueueADT queueADT) {
        return Objects.requireNonNull(queueADT).size;
    }

    /*
    is queue empty
    Pred: queueADT != null
    Post: R == (size == 0) && Immutable(queueADT, head, size) && size == size'
     */
    public static boolean isEmpty(final ArrayQueueADT queueADT) {
        return Objects.requireNonNull(queueADT).size == 0;
    }

    /*
    deletes all the elements from queue
    Pred: queueADT != null
    Post: size' == 0 && forall head <= i <= head + size - 1: queueADT.a'[i] == null
     */
    public static void clear(ArrayQueueADT queueADT) {
        Objects.requireNonNull(queueADT);
        while (!isEmpty(queueADT)) {
            dequeue(queueADT);
        }
        queueADT.size = queueADT.head = 0;
    }

    /*
    returns the number of occurrences of an element in the queue.
    Pred: queueADT != null && element != null
    Post: Immutable(queueADT, head, size) && size' == size && R == #{i: element.equals(elements[i])}
     */
    public static int count(ArrayQueueADT queueADT, Object element) {
        checkForNulls(queueADT, element);
        int cnt = 0;
        for (int i = 0; i < queueADT.size; i++) {
            if (checkElements(queueADT, i, element)) {
                cnt++;
            }
        }
        return cnt;
    }

    /*
    returns index of the first occurrence of an element in the queue or -1 if there are none.
    Pred: queueADT != null && element != null
    Post: Immutable(queueADT, head, size) && size' == size && R == min i: element.equals(elements[i])
    || -1 if there are none
     */
    public static int indexOf(ArrayQueueADT queueADT, final Object element) {
        checkForNulls(queueADT, element);
        for (int i = 0; i < queueADT.size; i++) {
            if (checkElements(queueADT, i, element)) {
                return i;
            }
        }
        return -1;
    }

    /*
    returns index of the last occurrence of an element in the queue or -1 if there are none.
    Pred: queueADT != null && element != null
    Post: Immutable(queueADT, head, size) && size' == size && R == max i: element.equals(elements[i])
    || -1 if there are none
     */
    public static int lastIndexOf(ArrayQueueADT queueADT, final Object element) {
        checkForNulls(queueADT, element);
        int lastIndex = -1;
        for (int i = 0; i < queueADT.size; i++) {
            if (checkElements(queueADT, i, element)) {
                lastIndex = i;
            }
        }
        return lastIndex;
    }

    private static void ensureCapacity(ArrayQueueADT queueADT, int size) {
        Objects.requireNonNull(queueADT);
        if (size > queueADT.cap) {
            queueADT.elements = rebuild(queueADT, 2 * size);
            queueADT.cap = queueADT.elements.length;
            queueADT.head = 0;
        }
    }

    private static Object[] rebuild(ArrayQueueADT queueADT, int capacity) {
        Objects.requireNonNull(queueADT);
        Object[] array = new Object[capacity];
        if (queueADT.head + queueADT.size - 1 < queueADT.cap) {
            System.arraycopy(queueADT.elements, queueADT.head, array, 0, queueADT.size);
        } else {
            System.arraycopy(queueADT.elements, queueADT.head, array, 0, queueADT.cap - queueADT.head);
            System.arraycopy(queueADT.elements, 0, array,
                    queueADT.cap - queueADT.head, queueADT.head + queueADT.size - queueADT.cap);
        }
        return array;
    }

    private static int next(ArrayQueueADT queueADT, int index) {
        return (index + 1) % Objects.requireNonNull(queueADT).cap;
    }

    private static void checkForNulls(final ArrayQueueADT queueADT, final Object element) {
        Objects.requireNonNull(queueADT);
        Objects.requireNonNull(element);
    }

    private static boolean checkElements(final ArrayQueueADT queueADT, final int index, final Object element) {
        return Objects.equals(element, queueADT.elements[(queueADT.head + index) % queueADT.cap]);
    }
}
