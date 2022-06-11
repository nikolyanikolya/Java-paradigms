package queue;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Predicate;


public class LinkedQueue extends AbstractQueue {
    private Node last = null;
    private Node first = null;

    @Override
    public void enqueueImpl(final Object element) {
        Node tmp = last;
        last = new Node(null, element);
        if (size == 1) {
            first = last;
        } else {
            tmp.next = last;
        }
    }

    @Override
    public Object elementImpl() {
        return first.element;
    }


    @Override
    public void dequeueImpl() {
        if (size == 0) {
            first = last = null;
        } else {
            first = first.next;
        }
    }

    @Override
    public int indexIf(Predicate<Object> predicate) {
        return search_element(predicate, true);
    }

    @Override
    public int lastIndexIf(Predicate<Object> predicate) {
        return search_element(predicate, false);
    }

    private boolean applyPredicate(final Object element, Predicate<Object> predicate) {
        return predicate.test(element);
    }

    private int search_element(Predicate<Object> predicate, boolean isFirst) {
        Node it = first;
        int i = 0;
        ArrayList<Integer> tmpList = new ArrayList<>();
        while (it != null) {
            if (applyPredicate(it.element, predicate)) {
                tmpList.add(i);
            }
            it = it.next;
            i++;
        }
        if (tmpList.isEmpty()) {
            return -1;
        }
        return isFirst ? tmpList.get(0) : tmpList.get(tmpList.size() - 1);
    }

    private static class Node {
        private Node next;
        private final Object element;

        public Node(Node next, Object element) {
            this.next = next;
            this.element = element;
        }
    }
}
