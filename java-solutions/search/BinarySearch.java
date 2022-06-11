package search;

public class BinarySearch {
    // Pred: forall i : args[i] -- a string representation of int
    // && args != null && args.length > 0 && a = [args[1], args[2].. ] -- non-increasing array
    // Post: output min i: a[i] <= (int)args[0] or a.length if it does not exist && Immutable(a)
    public static void main(String[] args) {
        //forall i : args[i] -- a string representation of int
        // && args != null && args.length > 0 && a = [args[1], args[2].. ] -- non-increasing array
        int x = Integer.parseInt(args[0]);
        // Pred && x == (int)args[0]
        Integer[] a = new Integer[args.length - 1];
        // a == Integer[args.length - 1] && Pred && x == (int)args[0] && a.length == args.length - 1
        int i = 1;
        // a == Integer[args.length - 1] && Pred && x == (int)args[0] && i == 1 && a.length == args.length - 1
        // I: forall 1<= j < i: a[j-1] == (int)args[j] && a == Integer[args.length - 1] && Pred && x == (int)args[0]
        //&& a.length == args.length - 1
        while (i < args.length) {
            // I && i < args.length
            // Note that: i < args.length && a.length == args.length - 1 -> i - 1 < a.length
            // I && i < args.length && i - 1 < a.length
            a[i - 1] = Integer.parseInt(args[i]);
            // forall 1<= j < i + 1: a[j-1] == (int)args[j] && a == Integer[args.length - 1]
            // && Pred && x == (int)args[0] &&  a.length == args.length - 1
            i++;
            // I
        }
        // i == args.length && I -> Pred
        int result = iterativeBinarySearch(a, x);
        // result == R && R >= 0 && (R == a.length || a[R] <= x)
        // && forall (i >= 0 && i < R) : a[i] > x && R <= a.length
        // Pred: r == a.length && l == 0 &&
        // forall i, j; i >= j: a[i] <= a[j] && l <= r && l >= 0 && r >= 0 && l <= a.length && r <= a.length
        // && forall i,  i < l: a[i] > x && (r == a.length || a[r] <= x)
        int resultRecursive = recursiveBinarySearch(a, x, 0, a.length);
        // resultRecursive == R && R >= 0 && R <= a.length &&
        // (R == a.length || a[R] <= x) && forall (i >= 0 && i < R) : a[i] > x
        // Note that: conditions 27-28 and 33-34 are equivalent
        assert result == resultRecursive;
        // result == resultRecursive == R &&
        // R >= 0 && R <= a.length &&
        // (R == a.length || a[R] <= x) && forall (i >= 0 && i < R) : a[i] > x
        System.out.println(result);
        // Post
    }
    // Immutable(a) : a.length == a`.length && for i: 0 .. a.length -1: a[i] == a'[i]
    // Pred: forall i, j; i >= j: a[i] <= a[j]
    // Post: R >= 0 && (R == a.length || a[R] <= x) &&
    // forall (i >= 0 && i < R) : a[i] > x && R <= a.length
    // && Immutable(a)
    private static int iterativeBinarySearch(final Integer[] a, final int x) {
        // forall i, j; i >= j: a[i] <= a[j] && Immutable(a)
        int l = 0;
        // l == 0 && (forall i >= 0 && i < l : a[i] > x) && forall i, j; i >= j: a[i] <= a[j]
        // Immutable(a)
        int r = a.length;
        // r == a.length && (forall i >= 0 && i < l : a[i] > x) && forall i, j; i >= j: a[i] <= a[j] && l >= 0
        // I: (r == a.length || a[r] <= x) && (forall i >= 0 && i < l : a[i] > x) && forall i, j; i >= j: a[i] <= a[j]
        // && r >= 0 && l >= 0 && r <= a.length && Immutable(a)
        while (r > l) {
            // I && r > l
            // Note that: r <= a.length && r > l -> r + l < 2 * a.length -> (r + l) / 2 < a.length
            // Note that: r >= 0 && l >= 0 -> (r + l) / 2 >= 0
            // 0 <= (r + l) / 2  < a.length && (r == a.length || a[r] <= x) && (forall i >= 0 && i < l : a[i] > x)
            // && r > l && forall i, j; i >= j: a[i] <= a[j]
            int m = (r + l) / 2;
            //  0 <= m < a.length && l <= m <= r && (r == a.length || a[r] <= x) &&
            //  (forall i >= 0 && i < l : a[i] > x) && r > l && forall i, j; i >= j: a[i] <= a[j] && r <= a.length &&
            // Immutable(a)
            if (a[m] > x) {
                // l <= m <= r && I && r > l && a[m] > x && 0 <= m < a.length
                // Note that: 0 <= m < a.length && l >= 0 && l <= m <= r && forall i, j; i >= j: a[i] <= a[j] -> a[m] <= a[l]
                // && m + 1 >= 0
                // a[m] <= a[l] && (r == a.length || a[r] <= x) && a[m] > x && forall i, j; i >= j: a[i] <= a[j] &&
                // r >= 0 && m + 1 >= 0 && r <= a.length
                // Note that: a[m] <= a[l] && a[m] > x && forall i, j; i >= j: a[i] <= a[j] ->
                // (forall i >= 0 && i < m + 1 : a[i] > x)
                // (r == a.length || a[r] <= x) && (forall i >= 0 && i < m + 1 : a[i] > x) && r >= 0 && m + 1 >= 0
                // && r <= a.length
                l = m + 1;
                // l == m + 1 && (r == a.length || a[r] <= x) && (forall i >= 0 && i < l : a[i] > x)
                // && r >= 0 && l >= 0 && r <= a.length && Immutable(a)
            } else {
                // l <= m <= r && (forall i >= 0 && i < l : a[i] > x)
                // && a[m] <= x && 0 <= m < a.length && forall i, j; i >= j: a[i] <= a[j]
                // && r >= 0 && l >= 0 && Immutable(a)
                r = m;
                // r == m && a[r] <= x && (forall i >= 0 && i < l : a[i] > x)
                // && forall i, j; i >= j: a[i] <= a[j] && r >= 0 && l >= 0 && r <= a.length
                // && Immutable(a)
            }
           // I
        }
        // r <= l && I
        // Note that: r <= l && (forall i >= 0 && i < l : a[i] > x) -> forall (i >= 0 && i < r) : a[i] > x
        // r>= 0 && (r == a.length || a[r] <= x) && forall (i >= 0 && i < r) : a[i] > x && r <= a.length && Immutable(a)
        return r;
    }
    // Pred: forall i, j; i >= j: a[i] <= a[j] && l <= r && l >= 0 && r >= 0 && l <= a.length && r <= a.length
    // && forall i,  i < l: a[i] > x && (r == a.length || a[r] <= x)
    // Post: R >= 0 && R <= r && (R == a.length || a[R] <= x) && forall (i >= l && i < R) : a[i] > x && Immutable(a)
    private static int recursiveBinarySearch(final Integer[] a, final int x, int l, int r) {
        // forall i, j; i >= j: a[i] <= a[j] && l >= 0 && r >= 0 && l <= a.length && r <= a.length && l <= r
        // && forall i,  i < l: a[i] > x && (r == a.length || a[r] <= x) && Immutable(a)
        if (l == r){
            // Pred : forall i, j; i >= j: a[i] <= a[j] && l <= r && forall i,  i < l: a[i] > x
            // && (r == a.length || a[r] <= x) &&
            // l >= 0 && r >= 0 && l <= a.length && r <= a.length &&
            // Immutable(a)
            return l;
            // Note that: l == r && (r == a.length || a[r] <= x) -> (l == a.length || a[l] <= x)
            // Note that: forall (i >= l && i < l) : a[i] > x - empty set -> suitable
            // Post: l >= 0 && l <= r && (l == a.length || a[l] <= x) && forall (i >= l && i < l) : a[i] > x
            // && Immutable(a)

        }
        // l < r && forall i, j; i >= j: a[i] <= a[j] && l < a.length && l >= 0 && r >= 0 && r <= a.length
        // && forall i,  i < l: a[i] > x && (r == a.length || a[r] <= x) && Immutable(a)
        // Note that: r > l -> (r + l) / 2 > l && (r + l) / 2 < r
        int m = (r + l) / 2;
        // forall i, j; i >= j: a[i] <= a[j] && l < m < r && l >= 0 && r >= 0 && r <= a.length && Immutable(a)
        // Note that: l < m < r && l >= 0 && r >= 0 && r <= a.length -> 0 < m < a.length
        if (a[m] > x){
            // forall i, j; i >= j: a[i] <= a[j] && l < m < r && l >= 0 && r >= 0 && r <= a.length
            // && a[m] > x && Immutable(a)
            // Note that: a[m] > x && forall i, j; i >= j: a[i] <= a[j] ->
            // forall i,  i <  m + 1: a[i] > x
            // Pred: forall i, j; i >= j: a[i] <= a[j] && m + 1 <= r
            // && forall i,  i <  m + 1: a[i] > x && (r == a.length || a[r] <= x)
            // &&  l < m < r && l >= 0 && r >= 0 && r <= a.length && Immutable(a)
            return recursiveBinarySearch(a, x, m + 1, r);
            // Post: R >= 0 && R <= r && (R == a.length || a[R] <= x) && forall (i >= m + 1 && i < R) : a[i] > x
            // && forall i, j; i >= j: a[i] <= a[j] && Immutable(a)
            // && a[m] > x &&  l < m < r
            // Note that: l >= 0 && r >= 0 && r <= a.length && l < m < r -> 0 <= l < a.length
            // Note that: a[m] > x &&  l < m < r && forall i, j; i >= j: a[i] <= a[j] ->
            // forall l <= i <= m: a[i] > x
            // forall l <= i <= m: a[i] > x && forall (i >= m + 1 && i < R) : a[i] > x
            // && R >= 0 && (R == a.length || a[R] <= x) && R <= r
            // R >= 0 && R <= r && (R == a.length || a[R] <= x) && forall (i >= l && i < R) : a[i] > x &&
            // Immutable(a)
        }
        // Pred: forall i, j; i >= j: a[i] <= a[j] && l < m < r && forall i,  i < l: a[i] > x && a[m] <= x
        // && l >= 0 && r >= 0 && r <= a.length && Immutable(a)
        return recursiveBinarySearch(a, x, l, m);
        // R >= 0 && R <= m && (R == a.length || a[R] <= x) && forall (i >= l && i < R) : a[i] > x &&
        // l < m < r && l >= 0 && r >= 0 && r <= a.length && a[m] <= x &&
        // forall i, j; i >= j: a[i] <= a[j] && Immutable(a)
        // Note that: l < m < r &&  R <= m -> R <= r
        // R >= 0 && R <= r && (R == a.length || a[R] <= x) && forall (i >= l && i < R) : a[i] > x
        // && Immutable(a)

    }
}
