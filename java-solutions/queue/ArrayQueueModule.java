package queue;
/*
a - synonym of 'elements'
Model: a[0] .. a[cap - 1], cap - capacity of queue
Invariant: forall head <= i <= head + size - 1: a[i] != null;  0<= size <= cap,
            0 <= head < capacity, next(index) = (index + 1) % cap
Immutable(queue, head, size) : forall i, head <= i <= head + size - 1: queue.a[i] == queue.a'[i]
 */
public class ArrayQueueModule{
    private static final ArrayQueueADT queueADT = new ArrayQueueADT();
    /* adds element to queue
    Pred: element != null
    Post: queueADT.size' == queueADT.size + 1 && Immutable(queueADT, head, size)
           && queueADT.a[queueADT.size] == element
    */
    public static void enqueue(final Object element) {
        ArrayQueueADT.enqueue(queueADT, element);
    }
    /*
    first element in queue
    Pred: queueADT.size >= 1
    Post: Immutable(queueADT, head, size) && size == size' && R == queueADT.a[queueADT.head]
     */
    public static Object element() {
        return ArrayQueueADT.element(queueADT);
    }
    /*
    deletes and returns first element in queue
    Pred: queueADT != null && queueADT.size >= 1
    Post: Immutable(queueADT, next(head), size) && size' == size - 1 && R == queueADT.a[queueADT.head]
            && head' == next(head)
     */
    public static Object dequeue() {
        return ArrayQueueADT.dequeue(queueADT);
    }
    /*
    queue size
    Pred: queueADT != null
    Post: R == size == size' && Immutable(queueADT, head, size)
     */
    public static int size() {
        return ArrayQueueADT.size(queueADT);
    }
    /*
    is queue empty
    Pred: queueADT != null
    Post: R == (size == 0) && Immutable(queueADT, head, size) && size == size'
     */
    public static boolean isEmpty() {
        return ArrayQueueADT.isEmpty(queueADT);
    }
    /*
    deletes all the elements from queue
    Pred: queueADT != null
    Post: size' == 0 && forall head <= i <= head + size - 1: queueADT.a'[i] == null
     */
    public static void clear() {
        ArrayQueueADT.clear(queueADT);
    }
    /*
    returns the number of occurrences of an element in the queue.
    Pred: element != null
    Post: Immutable(queueADT, head, size) && size' == size && R == #{i: element.equals(elements[i])}
     */
    public static int count(final Object element){
        return ArrayQueueADT.count(queueADT, element);
    }
    /*
    returns index of the first occurrence of an element in the queue or -1 if there are none.
    Pred: queueADT != null && element != null
    Post: Immutable(queueADT, head, size) && size' == size && R == min i: element.equals(elements[i])
    || -1 if there are none
     */
    public static int indexOf(final Object element){
        return ArrayQueueADT.indexOf(queueADT, element);
    }
    /*
    returns index of the last occurrence of an element in the queue or -1 if there are none.
    Pred: queueADT != null && element != null
    Post: Immutable(queueADT, head, size) && size' == size && R == max i: element.equals(elements[i])
    || -1 if there are none
     */
    public static int lastIndexOf(final Object element){
        return ArrayQueueADT.lastIndexOf(queueADT, element);
    }
}
