package expression.generic;

import expression.exceptions.ExpressionEvaluateException;
import expression.generic.evaluators.*;
import expression.generic.operators.ExecutedCommonExpression;
import expression.parser.ParserException;

import java.util.Map;

public class GenericTabulator implements Tabulator {
    private final Map<String, ExpressionExecutor<?>> executors = Map.of(
            "i", new IntegerExpressionExecutor(true),
            "u", new IntegerExpressionExecutor(false),
            "s", new ShortExpressionExecutor(),
            "f", new FloatExpressionExecutor(),
            "d", new DoubleExpressionExecutor(),
            "bi", new BigIntegerExpressionExecutor()
    );

    @Override
    public Object[][][] tabulate(
            String mode, String expression,
            int x1, int x2, int y1, int y2, int z1, int z2
    ) throws ParserException {
        return getTable(executors.get(mode), expression, x1, x2, y1, y2, z1, z2);
    }

    private <T extends Number & Comparable<T>> Object[][][] getTable(
            ExpressionExecutor<T> executor, String expression,
            int x1, int x2, int y1, int y2, int z1, int z2
    ) throws ParserException {
        Object[][][] calculation = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        ExecutedCommonExpression<T> expr = (new ExpressionParser<>(executor)).parse(expression);

        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    try {
                        calculation[i - x1][j - y1][k - z1] = expr.evaluate(
                                executor.castNumber(i),
                                executor.castNumber(j),
                                executor.castNumber(k)
                        );
                    } catch (ExpressionEvaluateException e) {
                        calculation[i - x1][j - y1][k - z1] = null;
                    }
                }
            }
        }

        return calculation;
    }
}
