package expression.generic;

import expression.generic.evaluators.ExpressionExecutor;
import expression.generic.operators.ExecutedCommonExpression;
import expression.generic.operators.ExecutedOperatorFactory;
import expression.parser.*;

public class ExpressionParser<T extends Number & Comparable<T>> {
    private final ExpressionExecutor<T> executor;

    public ExpressionParser(ExpressionExecutor<T> executor) {
        this.executor = executor;
    }

    public ExecutedCommonExpression<T> parse(String expression) throws ParserException {
        ExpressionParserImpl<ExecutedCommonExpression<T>> parser =
                new ExpressionParserImpl<>(expression, new ExecutedOperatorFactory<T>(executor));
        return parser.parse();
    }
}
