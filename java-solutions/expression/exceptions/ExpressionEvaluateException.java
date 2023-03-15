package expression.exceptions;

import expression.CommonExpression;

public class ExpressionEvaluateException extends ArithmeticException {
    public ExpressionEvaluateException(String message, CommonExpression expression) {
        super(message + " (for f(x, y, z)=" + expression.toMiniString());
    }

    public ExpressionEvaluateException(String message) {
        super(message);
    }
}
