package search;

import java.util.Objects;

public class BinarySearchShift {
    // Pred': forall i : args[i] -- a string representation of int && diff(a)
    // && args != null && args.length > 0 && a = [args[0], args[1].. ] -- array with existing shift
    // shift: forall i, j from [shift; a.length) i > j: a[i] > a[j]
    // && forall i, j from [0; shift) i > j: a[i] > a[j] && a.length - 1 >= shift >= 0
    // Post': output shift of array a
    public static void main(String[] args) {
        // Pred'
        Integer[] a = new Integer[args.length];
        // a == Integer[args.length] && Pred'  && a.length == args.length
        int i = 0;
        // a == Integer[args.length] && Pred' && i == 0 && a.length == args.length
        // I: forall 0<= j < i: a[j] == (int)args[j] && a == Integer[args.length] && Pred'
        //&& a.length == args.length
        while (i < args.length) {
            // I && i < args.length
            // Note that: i < args.length && a.length == args.length -> i < a.length
            // I && i < args.length && i < a.length
            a[i] = Integer.parseInt(Objects.requireNonNull(args[i]));
            // forall 0 <= j < i + 1: a[j] == (int)args[j] && a == Integer[args.length] && Pred'
            // &&  a.length == args.length
            i++;
            // I
        }
        // i == args.length && I -> Pred
        int result = iterativeBinarySearch(a);
        //result == shift: forall 0 < i < shift: a[i] > a[0] && a[shift] <= a[0] &&  a.length - 1 >= shift >= 0
        // && Immutable(a)
        // Pred: r == a.length - 1 && l == 0 &&
        // shift exists: forall i, j from [shift; a.length) i > j: a[i] > a[j] &&
        // forall i, j from [0; shift) i > j: a[i] > a[j] && a.length - 1 >= shift >= 0
        // & forall 0 < i <= l: a[i] > a[0] && 0 <= l < a.length && 0 <= r < a.length && r >= l
        int resultRecursive = recursiveBinarySearch(a, 0, a.length - 1);
        //Post: resultRecursive == shift: forall 0 < i < shift: a[i] > a[0] && a[shift] <= a[0]
        // &&  a.length -1 >= shift >= 0 && Immutable(a)
        // Note that 31 - 32 and 38 - 39 are equivalent
        assert result == resultRecursive;
        // result == resultRecursive == shift
        System.out.println(result);
        // Post'
    }

    /*  diff(a) : forall i != j : a[i] != a[j]
        Immutable(a) : a.length == a`.length && for i: 0 .. a.length -1: a[i] == a'[i]
        Pred:  shift exists: forall i, j from [shift; a.length) i > j: a[i] > a[j] &&
         forall i, j from [0; shift) i > j: a[i] > a[j] && a.length - 1 >= shift >= 0 && diff(a)
        Post: R == shift: forall 0 < i < shift: a[i] > a[0] && a[shift] <= a[0] &&  a.length - 1 >= shift >= 0
        && Immutable(a)
     */
    public static int iterativeBinarySearch(Integer[] a) {
        //shift exists: forall i, j from [shift; a.length) i > j: a[i] > a[j] &&
        //     forall i, j from [0; shift) i > j: a[i] > a[j]
        int r = a.length - 1;
        // r == a.length - 1 && shift exists
        if (a[r] >= a[0]) {
            //  r == a.length - 1 && shift exists && a[r] >= a[0]
            // Note that: a.length - 1 >= shift > 0 exists -> a[a.length - 1] < a[0] -> controversy
            // shift == 0
            return 0;
            // R == shift == 0: forall 0 <= i < shift: a[i] > a[0] && a[shift] <= a[0] &&  r >= shift >= 0
        }
        // a[r] < a[0] && r == a.length - 1 && shift exists
        int l = 0;
        // a[a.length - 1] < a[0] && shift exists && l == 0 && shift != 0
        // I: forall 0 <= i <= l: a[i] >= a[0] && a[r] < a[0] &&  0 <= l < a.length && 0 <= r < a.length
        // && Immutable(a) && a[a.length - 1] < a[0] && shift != 0
        while (r - l > 1) {
            // I && r > l + 1
            int m = (r + l) / 2;
            // r > l + 1 && m == (r + l) / 2 ->  m > (2l + 1) / 2 -> m >= l + 1
            // r > l + 1 && m == (r + l) / 2 -> m < (2r - 1) / 2 -> m <=  r - 1
            // l + 1 <= m <= r - 1 && I
            // Note that: 0 <= l < a.length && l < m < r && 0 <= r < a.length ->
            // 0 <= m < a.length
            if (a[m] >= a[0]) {
                // l + 1 <= m <= r - 1 && I && a[m] >= a[0]
                // Note that: shift != 0, shift exists, a[a.length - 1] < a[0] ->
                // forall a.length > i >= shift: a[i] < a[0]
                // But a[m] >= a[0] -> m < shift
                // m < shift &&  forall i, j from [0; shift) i > j: a[i] > a[j]
                // forall 0 < i <= m: a[i] >= a[0] &&  a[r] < a[0]
                // &&  0 <= l < a.length && 0 <= r < a.length && Immutable(a)
                l = m;
                //l == m && I: forall 0 <= i <= l: a[i] >= a[0] &&  a[r] < a[0]
                // &&  0 <= m < a.length && 0 <= r < a.length && Immutable(a)
            } else {
                // l + 1 <= m <= r - 1 && I && a[m] < a[0] && forall 0 <= i <= l: a[i] >= a[0]
                // && Immutable(a) && 0 <= l < a.length &&  0 <= m < a.length
                r = m;
                //r == m && I: forall 0 <= i <= l: a[i] >= a[0] && a[r] < a[0])
                // &&  0 <= l < a.length && 0 <= r < a.length && Immutable(a)
            }
            // I
        }
        //r <= l + 1 && I: forall 0 <= i <= l: a[i] >= a[0] && a[r] < a[0]
        // &&  0 <= l < a.length && 0 <= r < a.length
        // && Immutable(a) && a[a.length - 1] < a[0] && shift != 0
        // a[r] < a[0] && forall 0 <= i <= l: a[i] >= a[0] -> r >= l + 1
        // r >= l + 1 && r <= l + 1 -> r == l + 1
        // forall i, 0 < i < r : a[i] >= a[0] && diff(a) -> forall i, 0 < i < r : a[i] > a[0]
        // forall i, 0 < i < r : a[i] > a[0] && a[r] < a[0] && a.length > r >= 0
        return r;
        // forall i, 0 < i < r : a[i] > a[0] && a[r] < a[0] && a.length > r >= 0 && R == r
    }

    /*  diff(a) : forall i != j : a[i] != a[j]
        Immutable(a) : a.length == a`.length && for i: 0 .. a.length -1: a[i] == a'[i]
        Pred:  shift exists: forall i, j from [shift; a.length) i > j: a[i] > a[j] &&
         forall i, j from [0; shift) i > j: a[i] > a[j] && a.length - 1 >= shift >= 0
         && forall 0 < i <= l: a[i] > a[0] && 0 <= l < a.length && 0 <= r < a.length && r >= l
        Post: R == shift: forall 0 < i < shift: a[i] > a[0] && a[shift] <= a[0] &&  r >= shift >= l
        && Immutable(a)
     */
    public static int recursiveBinarySearch(Integer[] a, int l, int r) {
        //shift exists: forall i, j from [shift; a.length) i > j: a[i] > a[j] &&
        //forall i, j from [0; shift) i > j: a[i] > a[j] && a.length - 1 >= shift >= 0 && diff(a)
        //&& forall 0 < i <= l: a[i] > a[0] && 0 <= l < a.length && 0 <= r < a.length && Immutable(a)
        if (a[a.length - 1] >= a[0]) {
            // shift exists && a[a.length - 1] >= a[0]
            // Note that: a.length - 1 >= shift > 0 exists -> a[a.length - 1] < a[0] -> controversy
            // shift == 0 && Immutable(a)
            return 0;
            // R == shift == 0: forall 0 < i < shift: a[i] > a[0] && a[shift] <= a[0]
            // && a.length - 1 >= shift >= 0 && Immutable(a)
        }
        // a[r] <= a[0] && shift exists && Immutable(a)
        if (r <= l + 1) {
            // a[a.length - 1] <= a[0] && shift exists && r <= l + 1 && a[r] <= a[0] &&
            // forall 0 < i <= l: a[i] > a[0]
            // Note that: a[r] <= a[0], forall 0 < i <= l: a[i] > a[0] -> r > l -> r == l + 1
            //forall 0 < i < r: a[i] > a[0] && a[r] <= a[0] && a.length - 1 >= r >= 0 && Immutable(a)
            return r;
            //R == r: forall 0 < i < r: a[i] > a[0] && a[r] <= a[0] &&  a.length - 1 >= r >= 0 && Immutable(a)
        }
        // r > l + 1 && shift exists && a[a.length - 1] >= a[0] && Immutable(a)
        int m = (r + l) / 2;
        // r > l + 1 && m == (r + l) / 2 ->  m > (2l + 1) / 2 -> m >= l + 1
        // r > l + 1 && m == (r + l) / 2 -> m < (2r - 1) / 2 -> m <=  r - 1
        // l + 1 <= m <= r - 1 && I && Immutable(a)
        // Note that: 0 <= l < a.length && l < m < r && 0 <= r < a.length ->
        // 0 <= m < a.length
        if (a[m] >= a[0]) {
            // r > l + 1 && shift exists && a[a.length - 1] >= a[0]
            // && a[m] >= a[0] && 0 <= m < a.length && forall 0 < i <= l: a[i] > a[0]
            // && a[r] <= a[0] && l < m < r && Immutable(a)
            // Note that: shift != 0, shift exists, a[a.length - 1] < a[0] ->
            // forall a.length > i >= shift: a[i] < a[0]
            //But a[m] >= a[0] -> m < shift
            // m < shift &&  forall i, j from [0; shift) i > j: a[i] > a[j]
            // forall 0 < i <= m: a[i] > a[0] &&  a[r] <= a[0] && diff(a) && r > m
            // &&  0 <= l < a.length && 0 <= r < a.length && Immutable(a) && shift exists
            return recursiveBinarySearch(a, m, r);
            //  R == shift: forall 0 < i < shift: a[i] > a[0] && a[shift] <= a[0] &&  r >= shift >= m && Immutable(a)
            // m > l -> r >= shift >= l
            // R == shift: forall 0 < i < shift: a[i] > a[0] && a[shift] <= a[0] &&  r >= shift >= l && Immutable(a)
        }
        // r > l + 1 && shift exists && a[a.length - 1] >= a[0]
        // && a[m] < a[0] && 0 <= m < a.length && forall 0 < i <= l: a[i] > a[0]
        // && a[r] <= a[0] && l < m < r
        // Pred: r' > l + 1 && shift exists && a[a.length - 1] >= a[0]
        //        && a[r'] < a[0] && 0 <= r' < a.length && forall 0 < i <= l: a[i] > a[0] && l < r' && Immutable(a)
        return recursiveBinarySearch(a, l, m);
        // Post: R == shift: forall 0 < i < shift: a[i] > a[0] && a[shift] <= a[0] &&  m >= shift >=l && Immutable(a)
        // r > m -> r >= shift >= l
        // R == shift: forall 0 < i < shift: a[i] > a[0] && a[shift] <= a[0] &&  r >= shift >= l && Immutable(a)
    }
}
