package expression.exceptions;

import expression.CommonExpression;
import expression.Multiply;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply(CommonExpression left, CommonExpression right) {
        super(left, right);
    }

    @Override
    public int evaluateImpl(int a, int b) {
        if (a == Integer.MIN_VALUE && b < 0 || b == Integer.MIN_VALUE && a < 0) {
            throw new ExpressionOverflowException(this);
        }

        int res = a * b;
        if (a != 0 && res / a != b) {
            throw new ExpressionOverflowException(this);
        }

        return res;
    }

    public static boolean checkOverflow(int a, int b) {
        if (a > 0 && b > 0) {
            return a > Integer.MAX_VALUE / b;
        } else if (a < 0 && b < 0) {
            return b < Integer.MAX_VALUE / a;
        } else if (a < 0 && b > 0) {
            return a < Integer.MIN_VALUE / b;
        } else if (a > 0 && b < 0) {
            return b < Integer.MIN_VALUE / a;
        } else {
            return false;
        }
    }
}
