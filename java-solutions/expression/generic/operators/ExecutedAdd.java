package expression.generic.operators;

import expression.generic.evaluators.ExpressionExecutor;

public class ExecutedAdd<T extends Number & Comparable<T>> extends ExecutedAbstractBinaryOperator<T> {
    public ExecutedAdd(ExpressionExecutor<T> executor,
                       ExecutedCommonExpression<T> left, ExecutedCommonExpression<T> right) {
        super(executor, left, right, true, false, true, 100);
    }

    public String getOperatorSign() {
        return "+";
    }

    @Override
    public T evaluateImpl(T a, T b) {
        return executor.add(a, b);
    }
}
