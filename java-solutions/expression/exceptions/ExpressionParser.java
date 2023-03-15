package expression.exceptions;

import expression.CommonExpression;
import expression.parser.*;

public class ExpressionParser implements TripleParser {
    @Override
    public CommonExpression parse(String expression)
            throws ParserArgumentExpectedException, ParserEOFException,
            ParserUnexpectedCharException, ParserConstantOverflowException {
        ExpressionParserImpl<CommonExpression> parser =
                new ExpressionParserImpl<>(expression, new CheckedOperatorFactory());
        return parser.parse();
    }
}
