package queue;

import java.util.function.Predicate;

public class LinkedQueueTesting {
    private static void addElements(LinkedQueue queue){
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }
    }
    private static void dumpElements(LinkedQueue queue){
        while (!queue.isEmpty()) {
            var element = queue.element();
            System.out.println(queue.size() + " " +
                    element + " " +  queue.dequeue());
        }
    }
    private static void printIndex(LinkedQueue queue, Object element){
        System.out.println("Index of " + element + ": " + queue.indexIf(Predicate.isEqual(element)));
    }
    private static void printLastIndex(LinkedQueue queue, Object element){
        System.out.println("Last index of " + element + ": " +
                queue.lastIndexIf(Predicate.isEqual(element)));
    }
    public static void main(String[] args) {
        System.out.println("ArrayQueue Test\n==========================");
        LinkedQueue queue1 = new LinkedQueue();
        addElements(queue1);
        printIndex(queue1, 5);
        printIndex(queue1, 11);
        queue1.enqueue( 5);
        printLastIndex(queue1, 5);
        printLastIndex(queue1, 11);
        queue1.clear();
        System.out.println("size after clear: " + queue1.size());
        LinkedQueue queue2 = new LinkedQueue();
        addElements(queue2);
        dumpElements(queue2);
    }
}
