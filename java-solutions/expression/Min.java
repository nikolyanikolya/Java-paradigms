package expression;

import expression.calculator.Calculator;

public class Min<T extends Number> extends BinaryOperation<T> {
    public Min(TemplateExpression<T> a, TemplateExpression<T> b) {
        super(a, b);
    }

    @Override
    @SuppressWarnings("ManualMinMaxCalculation")
    protected int calculate(int x, int y) {
        return x < y ? x : y;
    }

    @Override
    protected T calculate(T x, T y, Calculator<T> calculator) {
        return calculator.min(x, y);
    }

    @Override
    protected String getOperation() {
        return "min";
    }
}