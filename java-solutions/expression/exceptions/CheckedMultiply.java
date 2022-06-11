package expression.exceptions;

import expression.TemplateExpression;


public class CheckedMultiply<T extends Number> extends expression.Multiply<T> {
    public CheckedMultiply(TemplateExpression<T> a, TemplateExpression<T> b) {
        super(a, b);
    }

    @Override
    protected int calculate(int x, int y) {
        return intCalculator.multiply(x, y);
    }

}