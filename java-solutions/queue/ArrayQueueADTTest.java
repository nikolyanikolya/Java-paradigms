package queue;

public class ArrayQueueADTTest {
    private static void addElements(ArrayQueueADT queue){
        for (int i = 0; i < 10; i++) {
            ArrayQueueADT.enqueue(queue, i);
        }
    }
    private static void dumpElements(ArrayQueueADT queue){
        while (!ArrayQueueADT.isEmpty(queue)) {
            var element = ArrayQueueADT.element(queue);
            System.out.println(ArrayQueueADT.size(queue) + " " +
                    element + " " + ArrayQueueADT.count(queue, element)  + " "
                    + ArrayQueueADT.dequeue(queue)
            );
        }
    }
    private static void printIndex(ArrayQueueADT queue, Object element){
        System.out.println("Index of " + element + ": " + ArrayQueueADT.indexOf(queue, element));
    }
    private static void printLastIndex(ArrayQueueADT queue, Object element){
        System.out.println("Last index of " + element + ": " + ArrayQueueADT.lastIndexOf(queue, element));
    }
    public static void main(String[] args) {
        System.out.println("ArrayQueueADT Test\n==========================");
        ArrayQueueADT queue1 = new ArrayQueueADT();
        addElements(queue1);
        printIndex(queue1, 5);
        printIndex(queue1, 11);
        ArrayQueueADT.enqueue(queue1, 5);
        printLastIndex(queue1, 5);
        printLastIndex(queue1, 11);
        ArrayQueueADT.clear(queue1);
        System.out.println("count after clear: " + ArrayQueueADT.count(queue1, 0));
        System.out.println("size after clear: " + ArrayQueueADT.size(queue1));
        ArrayQueueADT queue2 = new ArrayQueueADT();
        addElements(queue2);
        dumpElements(queue2);
    }
}
