package search;

public class BinarySearchMissing {
    // Pred: forall i : args[i] -- a string representation of int
    // && args != null && args.length > 0 && a = [args[1], args[2].. ] -- non-decreasing array
    // Post: output i where i -- min j: a[j] >= (int)args[0] && 0 <= j < a.length && a[j] == x
    // || -i - 1 where forall  0 <= j < i: a[j] < x && (i == length || a[i] > x)
    public static void main(String[] args) {
        //forall i : args[i] -- a string representation of int
        // && args != null && args.length > 0 && a = [args[1], args[2].. ] -- non-decreasing array
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
            // forall 1<= j < i + 1: a[j-1] == (int)args[j] && a == Integer[args.length - 1] && Pred && x == (int)args[0]
            // &&  a.length == args.length - 1
            i++;
            // I
        }
        // i == args.length && I -> Pred
        int result = iterativeBinarySearch(a, x);
        // Post: result == y : 0 <= y < a.length && a[y] == x && forall i, i < y: a[i] < x || result == -y - 1 &&
        // y >= 0 && (y == a.length || a[y] > x) && forall (i >= 0 && i < y) : a[i] < x && y <= a.length
        // Pred: r == a.length && l == 0 &&
        // forall i, j; i >= j: a[i] >= a[j] && l <= r && l >= 0 && r >= 0 && l <= a.length && r <= a.length
        // && forall i,  i < l: a[i] < x && (r == a.length || a[r] >= x)
        int resultRecursive = recursiveBinarySearch(a, x, 0, a.length);
        // resultRecursive == y : 0 <= y <= a.length && a[y] == x && forall i, 0 <= i < y: a[i] < x
        // && y < a.length || resultRecursive == -y - 1 &&
        //  a.length >= y >= 0 && (y == a.length || a[y] > x) &&
        //  forall (i >= 0 && i < y) : a[i] < x && y <= a.length
        // Note that: 28-29 and 34-37 are equivalent
        assert result == resultRecursive;
        // result == resultRecursive == R &&
        //R == y && 0 <= y < a.length && a[y] == x && forall i, i < y: a[i] < x || R == -y - 1 &&
        // y >= 0 && (y == a.length || a[y] > x) && forall (i >= 0 && i < y) : a[i] < x && y <= a.length
        // x == (int)args[0]
        System.out.println(result);
        // Post
    }
    // Immutable(a) : a.length == a`.length && for i: 0 .. a.length -1: a[i] == a'[i]
    // Pred: forall i, j; i >= j: a[i] >= a[j]
    // Post: R == y : 0 <= y < a.length && a[y] == x && forall i, i < y: a[i] < x || R == -y - 1 &&
    // y >= 0 && (y == a.length || a[y] > x) && forall (i >= 0 && i < y) : a[i] < x && y <= a.length
    private static int iterativeBinarySearch(Integer []a, final int x){
        // forall i, j; i >= j: a[i] >= a[j] && Immutable(a)
        int l = 0;
        // l == 0 && (forall i >= 0 && i < l : a[i] > x) && forall i, j; i >= j: a[i] >= a[j]
        // && Immutable(a)
        int r = a.length;
        // r == a.length && (forall i >= 0 && i < l : a[i] < x) && forall i, j; i >= j: a[i] >= a[j] && l >= 0
        // I: (r == a.length || a[r] >= x) && (forall i >= 0 && i < l : a[i] < x) && forall i, j; i >= j: a[i] >= a[j]
        // && r >= 0 && l >= 0 && r <= a.length && Immutable(a)
        while(r > l){
            // I && r > l
            // Note that: r <= a.length && r > l -> r + l < 2 * a.length -> (r + l) / 2 < a.length
            // Note that: r >= 0 && l >= 0 -> (r + l) / 2 >= 0
            // 0 <= (r + l) / 2  < a.length && (r == a.length || a[r] >= x) && (forall i >= 0 && i < l : a[i] < x)
            // && r > l && forall i, j; i >= j: a[i] >= a[j]
            int m = (r + l) / 2;
            //  0 <= m < a.length && l <= m <= r && (r == a.length || a[r] >= x) &&
            //  (forall i >= 0 && i < l : a[i] < x) && r > l && forall i, j; i >= j: a[i] >= a[j] && r <= a.length
            // && Immutable(a)
            if (a[m] < x){
                // l <= m <= r && I && r > l && a[m] < x && 0 <= m < a.length
                // Note that: 0 <= m < a.length && l >= 0 && l <= m <= r &&
                // forall i, j; i >= j: a[i] >= a[j] -> a[m] >= a[l] && m + 1 >= 0
                // a[m] >= a[l] && (r == a.length || a[r] >= x) && a[m] < x && forall i, j; i >= j: a[i] >= a[j] &&
                // r >= 0 && m + 1 >= 0 && r <= a.length
                // Note that: a[m] >= a[l] && a[m] < x && forall i, j; i >= j: a[i] >= a[j] ->
                // (forall i >= 0 && i < m + 1 : a[i] < x)
                // (r == a.length || a[r] >= x) && (forall i >= 0 && i < m + 1 : a[i] < x) && r >= 0 && m + 1 >= 0
                // && r <= a.length && forall i, j; i >= j: a[i] >= a[j]
                l = m + 1;
                // l == m + 1 && (r == a.length || a[r] >= x) && (forall i >= 0 && i < l : a[i] < x)
                // && r >= 0 && l >= 0 && r <= a.length && forall i, j; i >= j: a[i] >= a[j] && Immutable(a)
            }else {
                // l <= m <= r && (forall i >= 0 && i < l : a[i] < x)
                // && a[m] >= x && 0 <= m < a.length && forall i, j; i >= j: a[i] >= a[j]
                // && r >= 0 && l >= 0 && Immutable(a)
                r = m;
                // r == m && a[r] >= x && (forall i >= 0 && i < l : a[i] < x)
                // && forall i, j; i >= j: a[i] >= a[j] && r >= 0 && l >= 0 && r <= a.length
                // Immutable(a)
            }
            // I
        }
        // I && r <= l
        if(r != a.length && a[r] == x){
            // Note that: r <= l && (forall i >= 0 && i < l : a[i] < x) -> forall (i >= 0 && i < r) : a[i] < x
            //a[r] == x && (forall i >= 0 && i < r : a[i] < x) && forall i, j; i >= j: a[i] >= a[j]
            // && r >= 0 && l >= 0 && r < a.length && r <= l && Immutable(a)
            return r;
        }
        //(r == a.length || a[r] > x) && (forall i >= 0 && i < l : a[i] < x) && forall i, j; i >= j: a[i] >= a[j]
        // && r >= 0 && l >= 0 && r <= a.length && r <= l && Immutable(a)
        return -r - 1;
        // Immutable(a) && R == -r - 1 &&  (r == a.length || a[r] > x) && (forall i >= 0 && i < l : a[i] < x)
        // && forall i, j; i >= j: a[i] >= a[j]
        // && r >= 0 && l >= 0 && r <= a.length && r <= l
    }

    // Pred: forall i, j; i >= j: a[i] >= a[j] && l <= r && l >= 0 && r >= 0 && l <= a.length && r <= a.length
    // && forall i,  i < l: a[i] < x && (r == a.length || a[r] >= x)
    // Post: R == y : l <= y <= r && a[y] == x && forall i, l <= i < y: a[i] < x && y < a.length || R == -y - 1 &&
    // r >= y >= l && (y == a.length || a[y] > x) && forall (i >= l && i < y) : a[i] < x && y <= a.length
    private static int recursiveBinarySearch(Integer []a, int x, int l, int r){
        //forall i, j; i >= j: a[i] >= a[j] && l <= r && l >= 0 && r >= 0 && l <= a.length && r <= a.length
        // && forall i,  i < l: a[i] < x && (r == a.length || a[r] >= x) && Immutable(a)
        if (l == r){
            //forall i, j; i >= j: a[i] >= a[j] && l >= 0 && l <= a.length
            // && l == r && forall i,  i < l: a[i] < x && (l == a.length || a[l] >= x) && Immutable(a)
            if(l < a.length && a[l] == x) {
                //Pred : forall i, j; i >= j: a[i] >= a[j] && l >= 0  && l < a.length
                // && l == r && a[l] == x && forall i,  i < l: a[i] < x && Immutable(a)
                return l;
                // R == l && a[l] == x && forall i,  i < l: a[i] < x && l >= 0 && l < a.length
                // && Immutable(a)
            }
            //forall i, j; i >= j: a[i] >= a[j] && l >= 0 && l <= a.length
            // && l == r && forall i,  i < l: a[i] < x && (l == a.length || a[l] > x)
            // && Immutable(a)
            return -l - 1;
            // R == -l - 1 && forall i, j; i >= j: a[i] >= a[j] && l >= 0 && l <= a.length
            // && l == r && forall i,  i < l: a[i] < x && (l == a.length || a[l] > x)
            // && Immutable(a)
        }
        //forall i, j; i >= j: a[i] >= a[j] && l < r && l >= 0 && r >= 0 && l <= a.length && r <= a.length
        // && forall i,  i < l: a[i] < x && (r == a.length || a[r] >= x) && Immutable(a)
        // Note that: r > l -> (r + l) / 2 > l && (r + l) / 2 < r
        int m = (r + l) / 2;
        // forall i, j; i >= j: a[i] >= a[j] && l < r && l >= 0 && r >= 0 && l <= a.length && r <= a.length
        // && forall i,  i < l: a[i] < x && (r == a.length || a[r] >= x) && l < m < r && Immutable(a)
        if (a[m] < x){
            // Pred : forall i, j; i >= j: a[i] >= a[j] && l < r && l >= 0 && r >= 0 && l <= a.length && r <= a.length
            // && forall i,  i < l: a[i] < x && (r == a.length || a[r] >= x) && l < m < r && a[m] < x
            // && Immutable(a)
            return recursiveBinarySearch(a, x, m + 1, r);
            // Post: Immutable(a) && R == y : m + 1 <= y <= r && a[y] == x && forall i, m + 1 <= i < y: a[i] < x && y < a.length
            // || R == -y - 1 && r >= y >= m + 1 && (y == a.length || a[y] > x)
            // && forall (i >= m + 1 && i < y) : a[i] < x && y <= a.length
            // Note that: l >= 0 && r >= 0 && r <= a.length && l < m < r -> 0 <= l < a.length
            // Note that: a[m] < x &&  l < m < r && forall i, j; i >= j: a[i] >= a[j] ->
            // forall l <= i <= m: a[i] < x
            // Note that: forall l <= i <= m: a[i] < x && forall (i >= m + 1 && i < y) : a[i] < x
            // -> forall i, l <= i < y: a[i] < x
            // Note that: l < m < r && r >= y >= m + 1 -> r >= y >= l
            // Post: Immutable(a) && R == y : l <= y <= r && a[y] == x && forall i, l <= i < y: a[i] < x && y < a.length || R == -y - 1 &&
            // r >= y >= l && (y == a.length || a[y] > x) && forall (i >= l && i < y) : a[i] < x && y <= a.length
        }
        // Pred : forall i, j; i >= j: a[i] >= a[j] && l < r && l >= 0 && r >= 0 && l <= a.length && r <= a.length
        // && forall i,  i < l: a[i] < x && (r == a.length || a[r] >= x) && l < m < r && a[m] >= x
        // && Immutable(a)
        return recursiveBinarySearch(a, x, l, m);
        // Post: Immutable(a) && R == y : l <= y <= m && a[y] == x && forall i, l <= i < y: a[i] < x && y < a.length
        // || R == -y - 1 && m >= y >= l && (y == a.length || a[y] > x)
        // && forall (i >= l && i < y) : a[i] < x && y <= a.length
        // Note that: l < m < r &&  R == y && y <= m -> R <= r
        // Post: Immutable(a) && R == y : l <= y <= r && a[y] == x && forall i, l <= i < y: a[i] < x && y < a.length
        // || R == -y - 1 && r >= y >= l && (y == a.length || a[y] > x)
        // && forall (i >= l && i < y) : a[i] < x && y <= a.length

    }
}
