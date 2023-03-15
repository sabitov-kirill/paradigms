package expression.generic.evaluators;

public abstract class AbstractExpressionExecutor<T extends Number & Comparable<T>> implements ExpressionExecutor<T> {
    protected final T ZERO = castNumber(0);
    protected final T MINUS_ONE = castNumber(-1);

    @Override
    public T subtract(T x, T y) {
        return add(x, negate(y));
    }

    @Override
    public T negate(T x) {
        return multiply(x, MINUS_ONE);
    }

    @Override
    public T min(T x, T y) {
        return x.compareTo(y) > 0 ? y : x;
    }

    @Override
    public T max(T x, T y) {
        return x.compareTo(y) > 0 ? x : y;
    }

    @Override
    public T abs(T x) {
        if (x.compareTo(ZERO) < 0) {
            return negate(x);
        }
        return x;
    }
}
