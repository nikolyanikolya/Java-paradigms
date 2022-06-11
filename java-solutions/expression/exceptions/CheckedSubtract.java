package expression.exceptions;

import expression.TemplateExpression;


public class CheckedSubtract<T extends Number> extends expression.Subtract<T> {

    public CheckedSubtract(TemplateExpression<T> a, TemplateExpression<T> b) {
        super(a, b);
    }

    @Override
    protected int calculate(int x, int y) {
        return intCalculator.subtract(x, y);
    }


}