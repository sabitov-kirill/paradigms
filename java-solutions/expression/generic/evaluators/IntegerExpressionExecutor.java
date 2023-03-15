package expression.generic.evaluators;

import expression.exceptions.*;

public class IntegerExpressionExecutor extends AbstractExpressionExecutor<Integer> {
    private final boolean isOverflowChecking;

    public IntegerExpressionExecutor(boolean isOverflowChecking) {
        this.isOverflowChecking = isOverflowChecking;
    }

    @Override
    public Integer castNumber(int number) {
        return number;
    }

    @Override
    public Integer parseNumber(String number) {
        return Integer.parseInt(number);
    }

    @Override
    public Integer add(Integer x, Integer y) {
        if (isOverflowChecking && CheckedAdd.checkOverflow(x, y)) {
            throw new ExpressionOverflowException();
        }

        return x + y;
    }

    @Override
    public Integer subtract(Integer x, Integer y) {
        if (isOverflowChecking && CheckedSubtract.checkOverflow(x, y)) {
            throw new ExpressionOverflowException();
        }

        return x - y;
    }

    @Override
    public Integer multiply(Integer x, Integer y) {
        if (isOverflowChecking && CheckedMultiply.checkOverflow(x, y)) {
            throw new ExpressionOverflowException();
        }
        return x * y;
    }

    @Override
    public Integer divide(Integer x, Integer y) {
        if (isOverflowChecking && CheckedDivide.checkOverflow(x, y)) {
            throw new ExpressionOverflowException();
        }

        if (y == 0) {
            throw new ExpressionDivisionByZeroException();
        }

        return x / y;
    }
}
