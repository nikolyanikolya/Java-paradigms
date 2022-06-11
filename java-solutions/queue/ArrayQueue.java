package queue;

import java.util.function.Predicate;

public class ArrayQueue extends AbstractQueue {
    private static final int INITIAL_CAPACITY = 5;
    private int head = 0;
    private Object[] elements = new Object[INITIAL_CAPACITY];
    private int cap = elements.length;

    @Override
    public void enqueueImpl(final Object element) {
        ensureCapacity(size);
        elements[(head + size - 1) % cap] = element;
    }

    @Override
    public Object elementImpl() {
        return elements[head];
    }



    @Override
    public void dequeueImpl() {
        elements[head] = null;
        head = next(head);
    }

    @Override
    public void clearImpl() {
        size = head = 0;
    }


    /*
    returns index of the first occurrence of an element in the queue or -1 if there are none.
    Pred: element != null
    Post: Immutable(queueADT, head, size) && size' == size && R == min i: element.equals(elements[i])
    || -1 if there are none
     */

    public int indexOf(final Object element) {
        return search_element(Predicate.isEqual(element), -1, size - 1);
    }

    /*
    returns index of the last occurrence of an element in the queue or -1 if there are none.
    Pred: element != null
    Post: Immutable(queueADT, head, size) && size' == size && R == max i: element.equals(elements[i])
    || -1 if there are none
     */

    public int lastIndexOf(final Object element) {
        return search_element(Predicate.isEqual(element), 1, 0);
    }

    public int lastIndexIf(Predicate<Object> predicate) {
        return search_element(predicate, 1, 0);
    }

    public int indexIf(Predicate<Object> predicate) {
        return search_element(predicate, -1, size - 1);
    }

    private void ensureCapacity(int size) {
        if (size > cap) {
            elements = rebuild(2 * size);
            cap = elements.length;
            head = 0;
        }
    }

    private Object[] rebuild(int capacity) {
        Object[] array = new Object[capacity];
        if (head + size - 1 < cap) {
            System.arraycopy(elements, head, array, 0, size);
        } else {
            System.arraycopy(elements, head, array, 0, cap - head);
            System.arraycopy(elements, 0, array,
                    cap - head, head + size - cap);
        }
        return array;
    }

    private int next(int index) {
        return (index + 1) % cap;
    }


    private boolean applyPredicate(final int index, Predicate<Object> predicate) {
        return predicate.test(elements[(head + index) % cap]);
    }

    private int search_element(Predicate<Object> predicate, int reverse_order, int init) {
        int index = -1;
        for (int i = init; i < size && i >= 0; i += reverse_order) {
            if (applyPredicate(i, predicate)) {
                index = i;
            }
        }
        return index;
    }
}
