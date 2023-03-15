package expression.generic.operators;

import expression.ToMiniString;

public interface ExecutedCommonExpression<T extends Number & Comparable<T>> extends ToMiniString {
    T evaluate(T x, T y, T z);
}
