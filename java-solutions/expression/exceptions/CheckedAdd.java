package expression.exceptions;

import expression.TemplateExpression;


public class CheckedAdd<T extends Number> extends expression.Add<T> {
    public CheckedAdd(TemplateExpression<T> a, TemplateExpression<T> b) {
        super(a, b);
    }

    @Override
    protected int calculate(int x, int y) {
        return intCalculator.add(x, y);
    }

}
