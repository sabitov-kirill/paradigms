package expression.exceptions;

import expression.CommonExpression;

public class ExpressionOverflowException extends ExpressionEvaluateException {
    public ExpressionOverflowException(CommonExpression expression) {
        super("Overflow", expression);
    }

    public ExpressionOverflowException() {
        super("Overflow");
    }
}
