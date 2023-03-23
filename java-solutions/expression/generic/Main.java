package expression.generic;

/*
public class Main {

    public static void main(String[] args) throws Exception {
        assert args.length > 2 : "Not enough command line arguments.";

        Object[][][] values = new GenericTabulator().tabulate(
                args[0].substring(1),
                args[1],
                -2, 2, -2, 2, -2, 2
        );
        for (int i = 0; i <= 4; i++) {
            for (int j = 0; j <= 4; j++) {
                for (int k = 0; k <= 4; k++) {
                    System.out.println(values[i][j][k]);
                }
            }
        }
    }
}
*/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Main {
    private static boolean check(int[] arr1, int[] arr2) {
        int n = arr1.length;
        for (int a : arr1) {
            for (int b : arr2) {
                var set1 = set(arr1, a);
                var set2 = set(arr2, b);

                int s1 = set1.size();
                int s2 = set2.size();

                set1.retainAll(set2);

                int intSize = set1.size();

                if (intSize * n != s1 * s2) {
                    return false;
                }
            }
        }
        return true;
    }

    private static Set<Integer> set(int[] arr, int a) {
        Set<Integer> s = new TreeSet<>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] <= a) {
                s.add(i);
            }
        }

        return s;
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(Files.newBufferedReader(Path.of("data4_demo.txt")));
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        int[][] arr = new int[m][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                arr[j][i] = scanner.nextInt();
            }
        }

        int res = 0;

        for (int i = 0; i < m - 1; i++) {
            for (int j = i + 1; j < m; j++) {
                if (check(arr[i], arr[j])) {
                    res++;

                    if(i + 1 == 1) {
                        System.out.println(j + 1);
                    }
                }
            }
        }

        System.out.println(res);
    }

}
