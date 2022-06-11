package expression;

import expression.calculator.Calculator;

public class UpperBitCount<T extends Number> extends UnaryOperation<T> {

    public UpperBitCount(TemplateExpression<T> a) {
        super(a);
    }

    @Override
    protected String getOperation() {
        return "t0";
    }

    @Override
    protected int calculate(int x) {
        int digit = x;
        int cnt = 0;
        int parity = 0;
        if (digit == 0) {
            return 32;
        }
        if (digit < 0) {
            parity = 1;
            digit = -(digit + 1);
        }
        while (digit % 2 == parity) {
            digit /= 2;
            cnt++;
        }
        return cnt;
    }

    @Override
    protected T calculate(T x, Calculator<T> calculator) {
        return calculator.upperBit(x);
    }

}
