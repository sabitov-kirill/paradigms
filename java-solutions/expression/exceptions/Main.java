package expression.exceptions;

import expression.TripleExpression;
import expression.parser.ParserException;

public class Main {
    public static void main(String[] args) {
        ExpressionParser parser = new ExpressionParser();
        try {
            TripleExpression expr = parser.parse("1000000*x*x*x*x*x/(x-1)");

            System.out.println("  x  result");
            for (int i = 0; i <= 10; i++) {
                String result;
                try {
                    result = String.valueOf(expr.evaluate(i, 0, 0));
                } catch (ExpressionEvaluateException e) {
                    result = e.getMessage();
                }

                System.out.printf("%3d  %s\n", i, result);
            }
        } catch (ParserException e) {
            System.out.println("Parse error: " + e.getMessage());
        }
    }
}
