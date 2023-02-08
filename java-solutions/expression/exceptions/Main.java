package expression.exceptions;

import expression.TripleExpression;
import expression.parser.ParserException;g

public class Main {
    public static void main(String[] args) {
        ExpressionParser parser = new ExpressionParser();
        try {
            TripleExpression expr = parser.parse("x + y * 20 / z - count(z)");
            System.out.println(
                    "Expression: " + expr.toMiniString() +
                    "\nEvaluation: " + expr.evaluate(47, 30, 0)
            );
        } catch (ParserException e) {
            System.out.println("Parse error: " + e.getMessage());
        }
    }
}
