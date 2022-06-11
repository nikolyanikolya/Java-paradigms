package expression;

import expression.calculator.Calculator;

public class Max<T extends Number> extends BinaryOperation<T> {
    public Max(TemplateExpression<T> a, TemplateExpression<T> b) {
        super(a, b);
    }

    @Override
    @SuppressWarnings("ManualMinMaxCalculation")
    protected int calculate(int x, int y) {
        return x < y ? y : x;
    }

    @Override
    protected T calculate(T x, T y, Calculator<T> calculator) {
        return calculator.max(x, y);
    }

    @Override
    protected String getOperation() {
        return "max";
    }
}