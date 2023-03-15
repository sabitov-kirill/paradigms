
package expression.generic.operators;


import expression.generic.evaluators.ExpressionExecutor;

public abstract class ExecutedAbstractUnaryOperator<T extends Number & Comparable<T>>
        extends ExecutedAbstractOperator<T> {
    private final ExecutedCommonExpression<T> operand;

    public ExecutedAbstractUnaryOperator(ExpressionExecutor<T> executor,
                                         ExecutedCommonExpression<T> operand, int priority) {
        super(priority, executor);

        this.operand = operand;
    }

    public abstract String getOperatorSign();
    public abstract T evaluateImpl(T a);

    @Override
    public T evaluate(T x, T y, T z) {
        return evaluateImpl(operand.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return getOperatorSign() + "(" + operand + ")";
    }

    @Override
    public String toMiniString() {
        boolean brackets = operand instanceof ExecutedAbstractOperator<T> op && getPriority() > op.getPriority();
        return getOperatorSign() + (brackets ? "(" : " ") + operand.toMiniString() + (brackets ? ")" : "");
    }

    @Override
    public int hashCode() {
        return operand.hashCode() * 83 * getOperatorSign().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ExecutedAbstractUnaryOperator<?> otherBinaryOperation) {
            return operand.equals(otherBinaryOperation.operand);
        }
        return false;
    }
}
