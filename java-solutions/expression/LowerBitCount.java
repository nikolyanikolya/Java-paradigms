package expression;

import expression.calculator.Calculator;

public class LowerBitCount<T extends Number> extends UnaryOperation<T> {
    public LowerBitCount(TemplateExpression<T> a) {
        super(a);
    }

    @Override
    protected String getOperation() {
        return "l0";
    }

    @Override
    protected int calculate(int x) {
        int digit = x;
        int cnt = 0;
        if (digit >= 0) {
            while (digit > 0) {
                digit /= 2;
                cnt++;
            }
            return 32 - cnt;
        }
        return 0;
    }

    @Override
    protected T calculate(T x, Calculator<T> calculator) {
        return calculator.lowerBit(x);
    }
}
