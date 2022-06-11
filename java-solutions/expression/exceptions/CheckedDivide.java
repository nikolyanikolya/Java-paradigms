package expression.exceptions;

import expression.TemplateExpression;

public class CheckedDivide<T extends Number> extends expression.Divide<T> {

    public CheckedDivide(TemplateExpression<T> a, TemplateExpression<T> b) {
        super(a, b);
    }

    @Override
    protected int calculate(int x, int y) {
        return intCalculator.divide(x, y);
    }
}
