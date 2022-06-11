package expression.calculator;

import expression.exceptions.DivisionByZeroException;

import java.math.BigInteger;


public class BigIntegerCalculator implements Calculator<BigInteger>{
    @Override
    public BigInteger add(BigInteger a, BigInteger b) {
        return a.add(b);
    }

    @Override
    public BigInteger multiply(BigInteger a, BigInteger b) {
        return a.multiply(b);
    }

    @Override
    public BigInteger divide(BigInteger a, BigInteger b) {
        if(b.equals(BigInteger.valueOf(0))){
            throw new DivisionByZeroException("BigInteger divide by zero");
        }
        return a.divide(b);
    }

    @Override
    public BigInteger subtract(BigInteger a, BigInteger b) {
        return a.subtract(b);
    }

    @Override
    public BigInteger negate(BigInteger a) {
        return a.negate();
    }

    @Override
    public BigInteger min(BigInteger a, BigInteger b) {
        return a.min(b);
    }

    @Override
    public BigInteger max(BigInteger a, BigInteger b) {
        return a.max(b);
    }

    @Override
    public BigInteger parseString(String s) {
        return new BigInteger(s);
    }

    @Override
    public BigInteger lowerBit(BigInteger a) {
        return null;
    }

    @Override
    public BigInteger upperBit(BigInteger a) {
        return null;
    }

    @Override
    public BigInteger castFromInt(int a) {
        return BigInteger.valueOf(a);
    }

    @Override
    public BigInteger countBit(BigInteger a) {
        return BigInteger.valueOf(a.bitCount());
    }
}
