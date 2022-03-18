package search;
import java.util.Arrays;
import java.util.Collections;

public class BinarySearchMissing {
    public static void main(String[] args) {
        if (args == null || args.length < 1){
            System.err.println("At least one argument expected");
            return;
        }
        int x = Integer.parseInt(args[0]);
        Integer [] a = new Integer[args.length -1];
        for (int i = 1; i < args.length; i++){
            a[i - 1] = Integer.parseInt(args[i]);
        }
        Arrays.sort(a, Collections.reverseOrder());
        System.out.println(lowerBound(a, x));

    }
    private static int lowerBound(Integer []a, int x){
        if (a.length == 0){
            return -1;
        }
        int l = 0;
        int r = a.length;
        while(r > l){
            int m = l + (r-l) / 2;
            if (a[m] > x){
                l = m + 1;
            }else {
                r = m;
            }
        }
        return l;
    }
}
