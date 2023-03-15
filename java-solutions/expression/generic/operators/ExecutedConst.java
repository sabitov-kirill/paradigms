package expression.generic.operators;


public class ExecutedConst<T extends Number & Comparable<T>> implements ExecutedCommonExpression<T> {
    private final T value;

    public ExecutedConst(T value) {
        this.value = value;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ExecutedConst<?> otherConst) {
            return otherConst.value.equals(value);
        }
        return false;
    }
}
