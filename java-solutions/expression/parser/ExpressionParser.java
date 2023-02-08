package expression.parser;

import expression.DefaultOperatorFactory;
import expression.TripleExpression;

public class ExpressionParser implements TripleParser {
    @Override
    public TripleExpression parse(String expression) {
        ExpressionParserImpl parser = new ExpressionParserImpl(expression, new DefaultOperatorFactory());
        try {
            return parser.parse();
        } catch (ParserException e) {
            throw new IllegalArgumentException("Parse error: " + e.getMessage());
        }
    }
}
