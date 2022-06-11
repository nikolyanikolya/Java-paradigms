package expression.calculator;

import static java.lang.Double.parseDouble;

public class DoubleCalculator implements Calculator<Double>{

    @Override
    public Double add(Double a, Double b) {
        return a + b;
    }

    @Override
    public Double multiply(Double a, Double b) {
        return a * b;
    }

    @Override
    public Double divide(Double a, Double b) {
        return a / b;
    }

    @Override
    public Double subtract(Double a, Double b) {
        return a - b;
    }

    @Override
    public Double negate(Double a) {
        return -a;
    }

    @Override
    public Double min(Double a, Double b) {
        return Double.min(a, b);
    }

    @Override
    public Double max(Double a, Double b) {
        return Double.max(a, b);
    }

    @Override
    public Double parseString(String s) {
        return parseDouble(s);
    }

    @Override
    public Double lowerBit(Double a) {
        return 0.0;
    }

    @Override
    public Double upperBit(Double a) {
        return 0.0;
    }

    @Override
    public Double castFromInt(int a) {
        return (double)a;
    }

    @Override
    public Double countBit(Double a) {
        long bits = Double.doubleToLongBits(a);
        return (double)Long.bitCount(bits);
    }
}
