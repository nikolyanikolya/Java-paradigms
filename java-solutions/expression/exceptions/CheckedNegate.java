package expression.exceptions;

import expression.TemplateExpression;


public class CheckedNegate<T extends Number> extends expression.Negate<T> {

    public CheckedNegate(TemplateExpression<T> a) {
        super(a);
    }

    @Override
    protected int calculate(int x) {
        return intCalculator.negate(x);
    }
}