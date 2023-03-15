package expression.generic.operators;

import expression.generic.evaluators.ExpressionExecutor;

public class ExecutedSubtract<T extends Number & Comparable<T>> extends ExecutedAbstractBinaryOperator<T> {
    public ExecutedSubtract(ExpressionExecutor<T> executor,
                            ExecutedCommonExpression<T> left, ExecutedCommonExpression<T> right) {
        super(executor, left, right, false, false, true, 100);
    }

    @Override
    public String getOperatorSign() {
        return "-";
    }

    @Override
    public T evaluateImpl(T a, T b) {
        return executor.subtract(a, b);
    }
}
