package expression.calculator;

import expression.exceptions.DivisionByZeroException;

public class ShortCalculator implements Calculator<Short>{
    @Override
    public Short add(Short a, Short b) {
        return (short)(a + b);
    }

    @Override
    public Short multiply(Short a, Short b) {
        return (short)(a * b);
    }

    @Override
    public Short divide(Short a, Short b) {
        if (b.equals((short)0)){
            throw new DivisionByZeroException("Short divide 0");
        }
        return (short)(a / b);
    }

    @Override
    public Short subtract(Short a, Short b) {
        return (short)(a - b);
    }

    @Override
    public Short negate(Short a) {
        return (short)(-a);
    }

    @Override
    public Short min(Short a, Short b) {
        return a < b ? a : b;
    }

    @Override
    public Short max(Short a, Short b) {
        return a < b ? b : a;
    }

    @Override
    public Short parseString(String s) {
        return Short.parseShort(s);
    }

    @Override
    public Short lowerBit(Short a) {
        return null;
    }

    @Override
    public Short upperBit(Short a) {
        return null;
    }

    @Override
    public Short castFromInt(int a) {
        return (short)a;
    }

    @Override
    public Short countBit(Short a) {
        return (short)Integer.bitCount(a & 0b1111111111111111);
    }
}
