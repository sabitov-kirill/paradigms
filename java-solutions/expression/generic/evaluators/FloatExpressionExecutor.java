package expression.generic.evaluators;

public class FloatExpressionExecutor extends AbstractExpressionExecutor<Float> {
    @Override
    public Float castNumber(int number) {
        return (float) number;
    }

    @Override
    public Float parseNumber(String number) {
        return Float.parseFloat(number);
    }

    @Override
    public Float add(Float x, Float y) {
        return (x + y);
    }

    @Override
    public Float multiply(Float x, Float y) {
        return (x * y);
    }

    @Override
    public Float divide(Float x, Float y) {
        return (x / y);
    }
}
