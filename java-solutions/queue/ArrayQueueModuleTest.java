package queue;


public class ArrayQueueModuleTest {
    private static void addElements(){
        for (int i = 0; i < 10; i++) {
            ArrayQueueModule.enqueue(i);
        }
    }
    private static void dumpElements(){
        while (!ArrayQueueModule.isEmpty()) {
            var element = ArrayQueueModule.element();
            System.out.println(ArrayQueueModule.size() + " " +
                    element + " " + ArrayQueueModule.count(element) + " "
                    + ArrayQueueModule.dequeue()
            );
        }
    }
    private static void printIndex(Object element){
        System.out.println("Index of " + element + ": " + ArrayQueueModule.indexOf(element));
    }
    private static void printLastIndex(Object element){
        System.out.println("Last index of " + element + ": " + ArrayQueueModule.lastIndexOf(element));
    }
    public static void main(String[] args) {
        System.out.println("ArrayQueueModule Test\n==========================");
        addElements();
        printIndex(5);
        printIndex( 11);
        ArrayQueueModule.enqueue( 5);
        printLastIndex( 5);
        printLastIndex(11);
        ArrayQueueModule.clear();
        System.out.println("count after clear: " + ArrayQueueModule.count(0));
        System.out.println("size after clear: " + ArrayQueueModule.size());
        addElements();
        dumpElements();
    }
}
