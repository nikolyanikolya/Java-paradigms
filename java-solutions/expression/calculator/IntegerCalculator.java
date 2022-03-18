package expression.calculator;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;

public class IntegerCalculator implements Calculator<Integer> {

    @Override
    public Integer add(Integer a, Integer b) throws OverflowException{
        if ((a > 0 && b > 0 && a + b <= 0)
                || (a < 0 && b < 0 && a + b >= 0)) {
            throw new OverflowException(String.format("Overflow while calculating %d + %d", a, b));
        }
        return a + b;
    }

    @Override
    public Integer multiply(Integer a, Integer b) throws OverflowException{
        if ((a > 0 && b > 0 && b > MAX_VALUE / a)
                || (a < 0 && b < 0  && (b < -1*(MIN_VALUE / a) || b * a < 0))
                || (a > 0 && b < 0 && b < MIN_VALUE / a)
                || (a < 0 && b > 0 && a < MIN_VALUE / b))
                {
            throw new OverflowException(String.format("Overflow while calculating %d * (%d)", a, b));
        }
        return a * b;
    }

    @Override
    public Integer divide(Integer a, Integer b) throws DivisionByZeroException, OverflowException{
        if (b == 0) {
            throw new DivisionByZeroException(String.format("Division %d by zero", a));
        }
        if (a == MIN_VALUE && b == -1) {
            throw new OverflowException(String.format("Overflow while calculating %d / (%d)", a, b));
        }
        return a / b;
    }

    @Override
    public Integer subtract(Integer a, Integer b) throws OverflowException{
        if (b == MIN_VALUE && a >= 0) {
            throw new OverflowException(String.format("Overflow while calculating %d - (%d)", a, b));
        } else if (b == MIN_VALUE) {
            return a - b;
        }
        return add(a, -b);
    }

    @Override
    public Integer negate(Integer a) throws OverflowException{
        if (a == MIN_VALUE) {
            throw new OverflowException(String.format("Overflow while calculating -(%d)", a));
        }
        return -a;
    }

}
