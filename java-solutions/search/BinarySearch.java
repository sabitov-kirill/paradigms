package search;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class BinarySearch {
    // Define NonIncreasing(a: int[]):
    //     forall i, j in [0, a.len): i < j => a[i] >= a[j].
    // Define MinIndInNonIncreasing(a: int[], x: int):
    //     R >= 0 && R < a.len && a[R] <= x && (R == 0 || a[R - 1] > x) || (a.len == 0 && R == 0).

    // Pred: a.len > 0 && NonIncreasing(a).
    // Post: R == MinIndInNonIncreasing(a, x).
    private static int iterativeSearch(int[] a, int x) {
        // Pred: NonIncreasing(a).
        int l = -1;
        // Post: NonIncreasing(a) && l.

        // Pred: NonIncreasing(a) && l.
        int r = a.length;
        // Post: NonIncreasing(a) && l && r.

        // Define R: MinIndInNonIncreasing(a, x).
        // Define I: l < R <= r.
        // Pred: NonIncreasing(a) && l && r && I.
        while (r - l != 1) {
            // Pred: NonIncreasing(a) && l && r && I && r - l != 1.
            int m = (l + r) / 2;
            // Note: r - l != 1 && l < r => m < r.
            // Post: NonIncreasing(a) && l && r && I && m && m < r.
            // Define d: r - l.

            // Pred: NonIncreasing(a) && l && r && I && m && m < r.
            if (a[m] <= x) {
                // Pred:
                //     a[m] <= x && m < r && I <=>
                //     a[m] <= x && m < r && l < R <= r && (R == 0 || a[R - 1] > x).
                r = m;
                // Post:
                //     a[r'] <= x < a[R - 1] && r' - l >= 1 || R == 0 <=>
                //     l < R <= r' && r - l >= 1 || R == 0 <=>
                //     I && r - l >= 1.
            } else {
                // Pred:
                //     a[m] > x && I <=>
                //     a[m] > x && a[R] <= x && l' < R <= r' <=>
                l = m;
                // Post:
                //     a[l'] > x && a[R] <= x && R <= r' && r - l >= 1 <=>
                //     a[l'] > x >= a[R] && R <= r' && r - l >= 1 <=>
                //     I && r - l >= 1.
            }
            // Post: I && d' == d / 2 && r' - l' >= 1.
        }
        // Post:
        //     I && !(r - l != 1) <=>
        //     r' - l' == 1 && l' < R <= r' <=>
        //     r' - 1 < R <= r' <=>
        //     r' == R.
        return r;
    }

    // Pred: a.len > 0 && NonIncreasing(a).
    // Post: R == MinIndInNonIncreasing(a, x).
    private static int recursiveSearch(int[] a, int x) {
        return recursiveSearch(a, x, -1, a.length);
    }

    // Define I: l < MinIndInNonIncreasing(a, x) <= r.
    // Pred: a.len > 0 && NonIncreasing(a) && I.
    // Post: a.len > 0 && NonIncreasing(a) && I && R == MinIndInNonIncreasing(a, x).
    private static int recursiveSearch(int[] a, int x, int l, int r) {
        // Pred: a.len > 0 && NonIncreasing(a) && I.
        if (r - l <= 1) {
            // Pred: a.len > 0 && NonIncreasing(a) && I && r - l <= 1.
            return r;
            // Post:
            //     I && !(r - l != 1) <=>
            //     r - l' == 1 && l < R <= r <=>
            //     r - 1 < R <= r <=>
            //     r == R.
        }
        // Post: a.len > 0 && NonIncreasing(a) && I && r - l > 1.

        // Pred: a.len > 0 && NonIncreasing(a) && I && r - l > 1.
        int m = (l + r) / 2;
        // Note: r - l != 1 && l < r => m < r.
        // Post: a.len > 0 && NonIncreasing(a) && I && r - l > 1 && m && m < r.

        // Pred: a.len > 0 && NonIncreasing(a) && I && r - l > 1 && m && m < r.
        // Define R: MinIndInNonIncreasing(a, x).
        if (a[m] > x) {
            // Pred:
            //     a[m] > x && I && a.len > 0 && NonIncreasing(a) <=>
            //     a[m] > x && a[R] <= x && l < R <= r && a.len > 0 && NonIncreasing(a) <=>
            //     a[m] > x >= a[R] && R && l < R <= r && a.len > 0 && NonIncreasing(a) <=>
            //     m < R <= r && a.len > 0 && NonIncreasing(a).
            return recursiveSearch(a, x, m, r);
        } else {
            // Pred:
            //     a[m] <= x && m < r && I && a.len > 0 && NonIncreasing(a) <=>
            //     a[m] <= x && m < r && l < R <= r && (R == 0 || a[R - 1] > x) && a.len > 0 && NonIncreasing(a) <=>
            //     a[R - 1] > x >= a[m] && && l < R <= r && a.len > 0 && NonIncreasing(a) <=>
            //     l < R <= m && a.len > 0 && NonIncreasing(a).
            return recursiveSearch(a, x, l, m);
        }
    }

    // Pred:
    //     forall i in [0, args.len): args[i] can be cast to int && NonIncreasing(args[1 to args.len - 1]).
    // Post: (P := print), P == MinIndInNonIncreasing(args[1 to args.len - 1], args[0]).
    public static void main(String[] args) {
        // Pred: forall i in [0, args.len): args[i] can be cast to int && NonIncreasing(args[1 to args.len - 1])
        if (args.length <= 1) {
            // Pred: args.len <= 1 => args[1 to args.len - 1].len == 0.
            System.out.println(0);
            // Post:
            //     args[1 to args.len - 1].len == 0 && P == 0 =>
            //     P == MinIndInNonIncreasing(args[1 to args.len - 1], args[0]).
            return;
        }
        // Post: forall i in [0, args.len): args[i] can be cast to int && NonIncreasing(args[1 to args.len - 1]) &&
        //       args.len > 1

        // Pred: forall i in [0, args.len): args[i] can be cast to int && NonIncreasing(args[1 to args.len - 1]) &&
        //       args.len > 1.
        int x = Integer.parseInt(args[0]);
        // Post: forall i in [0, args.len): args[i] can be cast to int && NonIncreasing(args[1 to args.len - 1]) &&
        //       args.len > 1 && x.

        // Pred: forall i in [0, args.len): args[i] can be cast to int && NonIncreasing(args[1 to args.len - 1]) &&
        //       args.len > 1 && x.
        AtomicInteger sum = new AtomicInteger();
        int[] a = Arrays.stream(args).skip(1).mapToInt(argsi -> {
            int ai = Integer.parseInt(argsi);
            sum.addAndGet(ai);
            return ai;
        }).toArray();
        // Pred: forall i in [0, args.len): args[i] can be cast to int && NonIncreasing(args[1 to args.len - 1]) &&
        //       args.len > 1 && x  && a && a.len > 0 && sum && sum == sum_{i = 0}^{a.len} a_i.


        // Pred: x && a && a.len > 0 && NonIncreasing(a).
        int result = (sum.get() & 2) == 0 ? recursiveSearch(a, x) : iterativeSearch(a, x);

        /*
         * int resultIterative = iterativeSearch(a, x);
         * int resultRecursive = recursiveSearch(a, x);
         * assert resultIterative == resultRecursive :
         *         "One of realisation of binary search is wrong:\n" +
         *                 "recursive = " + resultRecursive + ", iterative = " + resultIterative;
         */
        // Post: result == MinIndInNonIncreasing(a, x).

        // Pred: result == MinIndInNonIncreasing(a, x).
        /* System.out.println(resultIterative); */
        System.out.println(result);
        // Post: P == MinIndInNonIncreasing(a, x).
    }
}
