package expression.generic.evaluators;

public class DoubleExpressionExecutor extends AbstractExpressionExecutor<Double> {
    @Override
    public Double castNumber(int number) {
        return (double) number;
    }

    @Override
    public Double parseNumber(String number) {
        return Double.parseDouble(number);
    }

    @Override
    public Double add(Double x, Double y) {
        return x + y;
    }

    @Override
    public Double multiply(Double x, Double y) {
        return x * y;
    }

    @Override
    public Double divide(Double x, Double y) {
        return x / y;
    }
}
