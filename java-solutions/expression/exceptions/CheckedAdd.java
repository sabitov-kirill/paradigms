package expression.exceptions;

import expression.Add;
import expression.CommonExpression;

public class CheckedAdd extends Add {
    public CheckedAdd(CommonExpression left, CommonExpression right) {
        super(left, right);
    }

    @Override
    public int evaluateImpl(int a, int b) {
        if (checkOverflow(a, b)) {
            throw new ExpressionOverflowException(this);
        }

        return a + b;
    }

    public static boolean checkOverflow(int a, int b) {
        return b > 0 && a > Integer.MAX_VALUE - b || b < 0 && a < Integer.MIN_VALUE - b;
    }
}
