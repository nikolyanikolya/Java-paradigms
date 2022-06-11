package queue;

public class ArrayQueueTesting {
    private static void addElements(ArrayQueue queue){
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }
    }
    private static void dumpElements(ArrayQueue queue){
        while (!queue.isEmpty()) {
            var element = queue.element();
            System.out.println(queue.size() + " " +
                    element + " " +  queue.dequeue());
        }
    }
    private static void printIndex(ArrayQueue queue, Object element){
        System.out.println("Index of " + element + ": " + queue.indexOf(element));
    }
    private static void printLastIndex(ArrayQueue queue, Object element){
        System.out.println("Last index of " + element + ": " + queue.lastIndexOf(element));
    }
    public static void main(String[] args) {
        System.out.println("ArrayQueue Test\n==========================");
        ArrayQueue queue1 = new ArrayQueue();
        addElements(queue1);
        printIndex(queue1, 5);
        printIndex(queue1, 11);
        queue1.enqueue( 5);
        printLastIndex(queue1, 5);
        printLastIndex(queue1, 11);
        queue1.clear();
        System.out.println("size after clear: " + queue1.size());
        ArrayQueue queue2 = new ArrayQueue();
        addElements(queue2);
        dumpElements(queue2);
    }
}
