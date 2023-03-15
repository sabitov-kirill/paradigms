package expression.generic.operators;

import expression.generic.evaluators.ExpressionExecutor;

public class ExecutedNegate<T extends Number & Comparable<T>> extends ExecutedAbstractUnaryOperator<T> {
    public ExecutedNegate(ExpressionExecutor<T> executor, ExecutedCommonExpression<T> operand) {
        super(executor, operand, 300);
    }

    @Override
    public String getOperatorSign() {
        return "-";
    }

    @Override
    public T evaluateImpl(T a) {
        return executor.negate(a);
    }
}
