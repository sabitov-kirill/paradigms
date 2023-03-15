package expression.exceptions;

import expression.CommonExpression;
import expression.Divide;

public class CheckedDivide extends Divide {
    public CheckedDivide(CommonExpression left, CommonExpression right) {
        super(left, right);
    }

    @Override
    public int evaluateImpl(int a, int b) {
        if (checkOverflow(a, b)) {
            throw new ExpressionOverflowException(this);
        }

        if (b == 0) {
            throw new ExpressionDivisionByZeroException(this);
        }

        return a / b;
    }

    public static boolean checkOverflow(int a, int b) {
        return a == Integer.MIN_VALUE && b == -1;
    }
}
