package expression.exceptions;

import expression.CommonExpression;

public class ExpressionDivisionByZeroException extends ExpressionEvaluateException {
    public ExpressionDivisionByZeroException(CommonExpression expression) {
        super("Division By Zero", expression);
    }

    public ExpressionDivisionByZeroException() {
        super("Division By Zero");
    }
}
