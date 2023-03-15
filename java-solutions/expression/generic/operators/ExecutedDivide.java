package expression.generic.operators;

import expression.generic.evaluators.ExpressionExecutor;

public class ExecutedDivide<T extends Number & Comparable<T>> extends ExecutedAbstractBinaryOperator<T> {
    public ExecutedDivide(ExpressionExecutor<T> executor,
                          ExecutedCommonExpression<T> left, ExecutedCommonExpression<T> right) {
        super(executor, left, right, false, false, false, 200);
    }

    @Override
    public String getOperatorSign() {
        return "/";
    }

    @Override
    public T evaluateImpl(T a, T b) {
        return executor.divide(a, b);
    }
}
