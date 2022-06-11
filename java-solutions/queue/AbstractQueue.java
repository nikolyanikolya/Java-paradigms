package queue;

import java.util.Objects;
import java.util.function.Predicate;

/*
Model: a[0] .. a[cap - 1], cap - capacity of queue
Invariant: forall head <= i <= head + size - 1: a[i] != null;
Immutable(queue, head, size) : forall i, head <= i <= head + size - 1: queue.a[i] == queue.a'[i]
 */
public abstract class AbstractQueue implements Queue {
    protected int size = 0;

    /* adds element to queue
   Pred: element != null
   Post: queue.size' == queue.size + 1 && Immutable(queue, head, size)
          && queue.a[queue.size] == element
   */
    @Override
    public void enqueue(final Object element) {
        Objects.requireNonNull(element);
        size++;
        enqueueImpl(element);
    }

    /*
   first element in queue
   Pred: queue.size >= 1
   Post: Immutable(queue, head, size)
   && queue.size == queue.size' && R == queue.a[queue.head]
    */
    @Override
    public Object element() {
        assert size >= 1;
        return elementImpl();
    }

    /*
    deletes and returns first element in queue
    Pred:  size >= 1
    Post: Immutable(queue, next(head), size)
    && queue.size' == queue.size - 1 && R == queue.a[queue.head]
            && queue.head' == next(queue.head)
     */
    @Override
    public Object dequeue() {
        assert size >= 1;
        final Object front = element();
        size--;
        dequeueImpl();
        return front;
    }
    /*
    queue size
    Pred: true
    Post: R == size == size' && Immutable(queue, head, size)
     */

    @Override
    public int size() {
        return size;
    }

    /*
    is queue empty
    Pred: true
    Post: R == (size == 0) && Immutable(queue, head, size) && size == size'
     */

    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    /*
    deletes all the elements from queue
    Pred: true
    Post: size' == 0 && forall head <= i <= head + size - 1: a'[i] == null
     */

    @Override
    public void clear() {
        while (!isEmpty()) {
            dequeue();
        }
        clearImpl();
    }

    /*
    returns index of the first element satisfying the predicate in the queue or -1 if there are none.
    Pred: element != null
    Post: Immutable(queueADT, head, size) && size' == size && R == min i: predicate.test(elements[i])
    || -1 if there are none
     */
    public abstract int indexIf(Predicate<Object> predicate);

    /*
    returns index of the last element satisfying the predicate in the queue or -1 if there are none.
    Pred: element != null
    Post: Immutable(queueADT, head, size) && size' == size && R == max i: predicate.test(elements[i])
    || -1 if there are none
     */
    public abstract int lastIndexIf(Predicate<Object> predicate);

    protected void clearImpl() {

    }

    protected abstract void dequeueImpl();

    protected abstract void enqueueImpl(final Object element);

    protected abstract Object elementImpl();

}
