package expression.calculator;

import expression.exceptions.DivisionByZeroException;


public class LongCalculator implements Calculator<Long>{
    @Override
    public Long add(Long a, Long b) {
        return a + b;
    }

    @Override
    public Long multiply(Long a, Long b) {
        return a * b;
    }

    @Override
    public Long divide(Long a, Long b) {
        if (b == 0){
            throw new DivisionByZeroException("Long divide 0");
        }
        return a / b;
    }

    @Override
    public Long subtract(Long a, Long b) {
        return a - b;
    }

    @Override
    public Long negate(Long a) {
        return (-a);
    }

    @Override
    public Long min(Long a, Long b) {
        return a < b ? a : b;
    }

    @Override
    public Long max(Long a, Long b) {
        return a < b ? b : a;
    }

    @Override
    public Long parseString(String s) {
        return Long.parseLong(s);
    }

    @Override
    public Long lowerBit(Long a) {
        return null;
    }

    @Override
    public Long upperBit(Long a) {
        return null;
    }

    @Override
    public Long castFromInt(int a) {
        return (long) a;
    }

    @Override
    public Long countBit(Long a) {
        return (long) Long.bitCount(a);
    }
}
