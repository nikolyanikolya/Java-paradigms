package expression.calculator;

import expression.exceptions.DivisionByZeroException;

public class UncheckedIntCalculator implements Calculator<Integer>{
    @Override
    public Integer add(Integer a, Integer b) {
        return a + b;
    }

    @Override
    public Integer multiply(Integer a, Integer b) {
        return a * b;
    }

    @Override
    public Integer divide(Integer a, Integer b) {
        if (b.equals(0)){
            throw new DivisionByZeroException("Integer divide 0");
        }
        return a / b;
    }

    @Override
    public Integer subtract(Integer a, Integer b) {
        return a - b;
    }

    @Override
    public Integer negate(Integer a) {
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
        return Integer.parseInt(s);
    }

    @Override
    public Integer lowerBit(Integer a) {
        return null;
    }

    @Override
    public Integer upperBit(Integer a) {
        return null;
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
