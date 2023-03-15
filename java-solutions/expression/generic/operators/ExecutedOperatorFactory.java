package expression.generic.operators;

import expression.*;
import expression.generic.evaluators.ExpressionExecutor;

public class ExecutedOperatorFactory<T extends Number & Comparable<T>>
        implements OperatorFactory<ExecutedCommonExpression<T>> {
    private final ExpressionExecutor<T> executor;

    public ExecutedOperatorFactory(ExpressionExecutor<T> executor) {
        this.executor = executor;
    }

    public ExecutedAbstractBinaryOperator<T> getBinaryOperator(
            String sign, ExecutedCommonExpression<T> left, ExecutedCommonExpression<T> right) {
        return switch (sign) {
            case "+" -> new ExecutedAdd<>(executor, left, right);
            case "-" -> new ExecutedSubtract<>(executor, left, right);
            case "*" -> new ExecutedMultiply<>(executor, left, right);
            case "/" -> new ExecutedDivide<>(executor, left, right);
            default -> throw new IllegalArgumentException("Unknown operator sign '" + sign + "'");
        };
    }

    public ExecutedAbstractUnaryOperator<T> getUnaryOperator(String sign, ExecutedCommonExpression<T> operand) {
        return switch (sign) {
            case "-" -> new ExecutedNegate<>(executor, operand);
            default -> throw new IllegalArgumentException("Unknown operator sign '" + sign + "'");
        };
    }

    public ExecutedVariable<T> getVariable(String name) {
        return new ExecutedVariable<>(name);
    }

    public ExecutedConst<T> getConst(int value) {
        return new ExecutedConst<>(executor.castNumber(value));
    }
}
