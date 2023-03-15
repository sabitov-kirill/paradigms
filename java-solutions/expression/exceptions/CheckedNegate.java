package expression.exceptions;

import expression.CommonExpression;
import expression.Negate;

public class CheckedNegate extends Negate {
    public CheckedNegate(CommonExpression operand) {
        super(operand);
    }

    @Override
    public int evaluateImpl(int a) {
        if (checkOverflow(a)) {
            throw new ExpressionOverflowException(this);
        }

        return -a;
    }

    public static boolean checkOverflow(int a) {
        return a == Integer.MIN_VALUE;
    }
}
