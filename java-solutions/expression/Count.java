package expression;


import expression.calculator.Calculator;
import expression.calculator.IntegerCalculator;

public class Count<T extends Number> extends UnaryOperation<T> {
    public Count(TemplateExpression<T> a) {
        super(a);
    }

    @Override
    protected int calculate(int x) {
        return new IntegerCalculator().countBit(x);
    }

    @Override
    protected T calculate(T x, Calculator<T> calculator) {
        return calculator.countBit(x);
    }


    @Override
    protected String getOperation() {
        return "count";
    }
}
