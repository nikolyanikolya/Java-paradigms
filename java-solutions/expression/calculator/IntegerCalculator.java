package expression.calculator;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;

import static java.lang.Integer.*;

public class IntegerCalculator implements Calculator<Integer> {

    @Override
    public Integer add(Integer a, Integer b) throws OverflowException {
        if ((a > 0 && b > 0 && a + b <= 0)
                || (a < 0 && b < 0 && a + b >= 0)) {
            throw new OverflowException(String.format("Overflow while calculating %d + %d", a, b));
        }
        return a + b;
    }

    @Override
    public Integer multiply(Integer a, Integer b) throws OverflowException {
        if ((a > 0 && b > 0 && b > MAX_VALUE / a)
                || (a < 0 && b < 0 && (b < -1 * (MIN_VALUE / a) || b * a < 0))
                || (a > 0 && b < 0 && b < MIN_VALUE / a)
                || (a < 0 && b > 0 && a < MIN_VALUE / b)) {
            throw new OverflowException(String.format("Overflow while calculating %d * (%d)", a, b));
        }
        return a * b;
    }

    @Override
    public Integer divide(Integer a, Integer b) throws DivisionByZeroException, OverflowException {
        if (b == 0) {
            throw new DivisionByZeroException(String.format("Division %d by zero", a));
        }
        if (a == MIN_VALUE && b == -1) {
            throw new OverflowException(String.format("Overflow while calculating %d / (%d)", a, b));
        }
        return a / b;
    }

    @Override
    public Integer subtract(Integer a, Integer b) throws OverflowException {
        if (b == MIN_VALUE && a >= 0) {
            throw new OverflowException(String.format("Overflow while calculating %d - (%d)", a, b));
        } else if (b == MIN_VALUE) {
            return a - b;
        }
        return add(a, -b);
    }

    @Override
    public Integer negate(Integer a) throws OverflowException {
        if (a == MIN_VALUE) {
            throw new OverflowException(String.format("Overflow while calculating -(%d)", a));
        }
        return -a;
    }

    @Override
    public Integer min(Integer a, Integer b) {
        return a < b ? a : b;
    }

    @Override
    public Integer max(Integer a, Integer b) {
        return a < b ? b : a;
    }

    @Override
    public Integer parseString(String s) {
        return parseInt(s);
    }

    @Override
    public Integer lowerBit(Integer a) {
        int digit = a;
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
    public Integer upperBit(Integer a) {
        int digit = a;
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
    public Integer castFromInt(int a) {
        return a;
    }

    @Override
    public Integer countBit(Integer a) {
        return Integer.bitCount(a);
    }

}
