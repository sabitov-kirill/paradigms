package search;

import java.util.Arrays;

public class BinarySearchMax {
    // Define NonDecreasingAndShifted(a: int[]):
    //     exists k in [0, a.len): forall i, j in Nat: i < j => a[(i - k) mod a.len] < a[(j - k) mod a.len].

    // Pred: a && a.len > 0 && NonDecreasingAndShifted(a).
    // Post: R == max(a).
    private static int findMax(int[] a) {
        // Pred: a && a.len > 0 && NonDecreasingAndShifted(a).
        int l = -1;
        int r = a.length;
        // Post: a && a.len > 0 && NonDecreasingAndShifted(a) && l && r.

        // Define I: l < k <= r.
        // Pred: a && a.len > 0 && NonDecreasingAndShifted(a) && l && r && I.
        while (r - l > 1) {
            // Pred: NonDecreasingAndShifted(a) && I.
            int m = (l + r) / 2;
            // Note: r - l != 1 && l < r => m < r.
            // Post: NonDecreasingAndShifted(a) && l && r && I && m && m < r.

            // Pred: NonDecreasingAndShifted(a) && l && r && I && m && m < r.
            int left = (m - 1 + a.length) % a.length; // (m - 1) mod a.len
            int right = (m + 1) % a.length;           // (m + 1) mod a.len
            if (a[m] <= a[left] && a[m] <= a[right]) {
                // Pred:
                //     NonDecreasingAndShifted(a) && I && m < r &&
                //         a[m] >= a[(m - 1) mod a.len] && a[m] >= a[(m + 1) mod a.len] <=>
                //     forall i, j in Nat: i < j => a[(i - m) mod a.len] >= a[(j - m) mod a.len] <=>
                //     a[(m - 1) mod a.len] == max(a).
                return a[left];
                // Post: R == max(a).
            } else if (a[m] <= a[r - 1]) {
                // Pred:
                //     NonDecreasingAndShifted(a) && I && m < r && a[m] <= a[r - 1] <=>
                //     NonDecreasingAndShifted(a) && k in [l, m).
                r = m;
                // Post: NonDecreasingAndShifted(a) && I'.
            } else if (a[m] >= a[l + 1]) {
                // Pred:
                //     NonDecreasingAndShifted(a) && I && m < r && a[m] >= a[l + 1] <=>
                //     NonDecreasingAndShifted(a) && k in [m, r).
                l = m;
                // Post: NonDecreasingAndShifted(a) && I'.
            }
            // Post: NonDecreasingAndShifted(a) && I'.
        }
        // Post:
        //     I && (r - l == 1) <=>
        //     forall i, j in Nat: i < j => a[i] < a[j] <=>
        //     R == k == 0.
        return 0;
    }

    // Pred:
    //     forall i in [0, args.len): args[i] can be cast to int &&
    //     NonDecreasingAndShifted(args).
    // Post: (P := print), P == max(args).
    public static void main(String[] args) {
        // Pred:
        //     forall i in [0, args.len): args[i] can be cast to int &&
        //     NonDecreasingAndShifted(args).
        if (args.length < 1) {
            // Pred:
            //     args.len <= 1 <=> a.len == 0 <=>
            //     forall k in Nat:
            //         forall i, j in Nat: i < j => args[(i - k) mod args.len] >= args[(j - k) mod args.len].
            System.out.println(0);
            // Post: P == k.
            return;
        }

        // Pred:
        //     forall i in [0, args.len): args[i] can be cast to int &&
        //     NonDecreasingAndShifted(a) &&
        //     args.len > 1.
        int[] a = Arrays.stream(args).mapToInt(Integer::parseInt).toArray();
        // Post:
        //     a && a.len > 0 &&
        //     exists k in Nat: forall i, j in Nat: i < j => a[(i - k) mod a.len] >= a[(j - k) mod a.len].

        // Pred:
        //     a && a.len > 0 &&
        //     exists k in Nat: forall i, j in Nat: i < j => a[(i - k) mod a.len] >= a[(j - k) mod a.len].
        System.out.println(findMax(a));
        // Post: P == k.
    }
}
