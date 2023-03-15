package expression;

public class Add extends AbstractBinaryOperator {
    public Add(CommonExpression left, CommonExpression right) {
        super(left, right, true, false, true, 100);
    }

    @Override
    public String getOperatorSign() {
        return "+";
    }

    @Override
    public int evaluateImpl(int a, int b) {
        return a + b;
    }

    @Override
    public double evaluateImpl(double a, double b) {
        return a + b;
    }
}
