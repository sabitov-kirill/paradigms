package expression.generic.evaluators;

import expression.exceptions.ExpressionDivisionByZeroException;

import java.math.BigInteger;

public class BigIntegerExpressionExecutor extends AbstractExpressionExecutor<BigInteger> {
    @Override
    public BigInteger castNumber(int number) {
        return BigInteger.valueOf(number);
    }

    @Override
    public BigInteger parseNumber(String number) {
        return new BigInteger(number);
    }

    @Override
    public BigInteger add(BigInteger x, BigInteger y) {
        return x.add(y);
    }

    @Override
    public BigInteger multiply(BigInteger x, BigInteger y) {
        return x.multiply(y);
    }

    @Override
    public BigInteger divide(BigInteger x, BigInteger y) {
        if (y.equals(ZERO)) {
            throw new ExpressionDivisionByZeroException();
        }

        return x.divide(y);
    }
}
