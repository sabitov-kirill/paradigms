package expression;

public interface OperatorFactory<T> {
    T getBinaryOperator(String sign, T left, T right);
    T getUnaryOperator(String sign, T operand);
    T getVariable(String name);
    T getConst(int value);
}
