package expression.generic.operators;

import expression.generic.evaluators.ExpressionExecutor;

public abstract class ExecutedAbstractOperator<T extends Number & Comparable<T>> implements ExecutedCommonExpression<T> {
    private final int priority;
    protected final ExpressionExecutor<T> executor;

    protected int getPriority() {
        return priority;
    }

    protected ExecutedAbstractOperator(int priority, ExpressionExecutor<T> executor) {
        this.priority = priority;
        this.executor = executor;
    }
}
