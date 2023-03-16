package expression.generic.evaluators;

import expression.exceptions.ExpressionDivisionByZeroException;

public class ShortExpressionExecutor extends AbstractExpressionExecutor<Short> {
    @Override
    public Short castNumber(int number) {
        return (short) number;
    }

    @Override
    public Short parseNumber(String number) {
        return Short.parseShort(number);
    }

    @Override
    public Short add(Short x, Short y) {
        return (short) (x + y);
    }

    @Override
    public Short multiply(Short x, Short y) {
        return (short) (x * y);
    }

    @Override
    public Short divide(Short x, Short y) {
        if (y.equals(ZERO)) {
            throw new ExpressionDivisionByZeroException();
        }
        return (short) (x / y);
    }
}
