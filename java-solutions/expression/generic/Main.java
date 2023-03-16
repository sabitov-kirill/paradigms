package expression.generic;

public class Main {

    public static void main(String[] args) throws Exception {
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
