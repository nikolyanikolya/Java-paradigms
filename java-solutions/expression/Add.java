package expression;


import expression.calculator.Calculator;

public class Add<T extends Number> extends BinaryOperation<T> {
    public Add(TemplateExpression<T> a, TemplateExpression<T> b) {
        super(a, b);
    }

    @Override
    protected int calculate(int x, int y) {
        return x + y;
    }

    @Override
    protected T calculate(T x, T y, Calculator<T> calculator) {
        return calculator.add(x, y);
    }

    @Override
    protected String getOperation() {
        return "+";
    }

}
