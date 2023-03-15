package expression.generic.operators;

import expression.generic.evaluators.ExpressionExecutor;

public class ExecutedMultiply<T extends Number & Comparable<T>> extends ExecutedAbstractBinaryOperator<T> {
    public ExecutedMultiply(ExpressionExecutor<T> executor,
                            ExecutedCommonExpression<T> left, ExecutedCommonExpression<T> right) {
        super(executor, left, right, true, true, true, 200);
    }

    @Override
    public String getOperatorSign() {
        return "*";
    }

    @Override
    public T evaluateImpl(T a, T b) {
        return executor.multiply(a, b);
    }
}
