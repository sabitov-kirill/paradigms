package expression.generic.operators;

public class ExecutedVariable<T extends Number & Comparable<T>> implements ExecutedCommonExpression<T> {
    private final String name;

    public ExecutedVariable(String name) {
        this.name = name;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return switch (name) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> throw new IllegalArgumentException("Unsupported variable name '" + name + "'");
        };
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ExecutedVariable<?> otherVariable
                && otherVariable.name.equals(name);
    }
}
