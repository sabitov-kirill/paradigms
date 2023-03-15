
package expression.generic.operators;

import expression.generic.evaluators.ExpressionExecutor;
import expression.generic.operators.ExecutedAbstractOperator;

public abstract class ExecutedAbstractBinaryOperator<T extends Number & Comparable<T>> extends ExecutedAbstractOperator<T> {
    private final ExecutedCommonExpression<T> left, right;
    private final boolean associative;
    private final boolean distributivity;
    private final boolean distributive;

    public ExecutedAbstractBinaryOperator(ExpressionExecutor<T> executor,
                                          ExecutedCommonExpression<T> left, ExecutedCommonExpression<T> right,
                                          boolean associative, boolean distributivity, boolean distributive,
                                          int priority) {
        super(priority, executor);

        this.left = left;
        this.right = right;
        this.associative = associative;
        this.distributivity = distributivity;
        this.distributive = distributive;
    }

    public abstract String getOperatorSign();

    public abstract T evaluateImpl(T a, T b);

    @Override
    public T evaluate(T x, T y, T z) {
        return evaluateImpl(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return "(" + left + " " + getOperatorSign() + " " + right + ")";
    }

    @Override
    public String toMiniString() {
        boolean leftBrackets = left instanceof ExecutedAbstractBinaryOperator<?> op && getPriority() > op.getPriority();
        boolean rightBrackets = right instanceof ExecutedAbstractBinaryOperator<?> op
                && (getPriority() > op.getPriority() || (!associative && getPriority() == op.getPriority())
                || (this.distributivity && !op.distributive));

        String leftString = leftBrackets ? "(" + left.toMiniString() + ")" : left.toMiniString();
        String rightString = rightBrackets ? "(" + right.toMiniString() + ")" : right.toMiniString();

        return leftString + " " + getOperatorSign() + " " + rightString;
    }

    @Override
    public int hashCode() {
        return (left.hashCode() * 47 - right.hashCode() * 29) * getOperatorSign().hashCode() * 3;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ExecutedAbstractBinaryOperator<?> otherBinaryOperation) {
            return left.equals(otherBinaryOperation.left) && right.equals(otherBinaryOperation.right);
        }
        return false;
    }
}
