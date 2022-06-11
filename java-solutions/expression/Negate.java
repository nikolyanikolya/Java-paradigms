package expression;


import expression.calculator.Calculator;

public class Negate<T extends Number> extends UnaryOperation<T> {
    public Negate(TemplateExpression<T> a) {
        super(a);
    }

    @Override
    protected int calculate(int x) {
        return -x;
    }

    @Override
    protected T calculate(T x, Calculator<T> calculator) {
        return calculator.negate(x);
    }


    @Override
    protected String getOperation() {
        return "-";
    }
}
