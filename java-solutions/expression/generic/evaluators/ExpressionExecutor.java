package expression.generic.evaluators;

public interface ExpressionExecutor<T extends Number & Comparable<T>> {
    /* Constant getter functions. */
    T castNumber(int number);
    T parseNumber(String number);

    /* Default arithmetic operations. */
    T add(T x, T y);
    T subtract(T x, T y);
    T multiply(T x, T y);
    T divide(T x, T y);
    T negate(T x);

    T min(T x, T y);
    T max(T x, T y);
    T abs(T x);
}
