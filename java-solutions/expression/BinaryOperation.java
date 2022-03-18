package expression;

import expression.calculator.Calculator;
import expression.calculator.IntegerCalculator;

import java.util.Objects;

public abstract class BinaryOperation extends Operation {
    protected final Calculator<Integer> intCalculator = new IntegerCalculator();
    private final TemplateExpression a;
    private final TemplateExpression b;
    public BinaryOperation(TemplateExpression a, TemplateExpression b) {
        this.a = a;
        this.b = b;
    }

    protected abstract int calculate(int x, int y);


    @Override
    public int evaluate(int value) {
        return calculate(a.evaluate(value), b.evaluate(value));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return calculate(a.evaluate(x, y, z), b.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return '(' + a.toString() + ' ' + getOperation() +
                ' ' + b.toString() + ')';
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BinaryOperation binaryOperation) {
            return Objects.equals(binaryOperation.a, a) && Objects.equals(binaryOperation.b, b);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return  Objects.hash(a, b, getClass());
    }

}