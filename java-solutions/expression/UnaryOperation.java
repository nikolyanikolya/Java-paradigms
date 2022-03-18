package expression;


import expression.calculator.Calculator;
import expression.calculator.IntegerCalculator;

import java.util.Objects;

public abstract class UnaryOperation extends Operation {
    protected final Calculator<Integer> intCalculator = new IntegerCalculator();
    protected final TemplateExpression a;
    protected UnaryOperation(TemplateExpression a) {
        this.a = a;
    }
    @Override
    public int evaluate(int x, int y, int z) {
        return calculate(a.evaluate(x, y, z));
    }

    @Override
    public int evaluate(int x) {
        return calculate(a.evaluate(x));
    }
    protected abstract int calculate(int x);

    @Override
    public String toString() {
        return getOperation() + "(" + a.toString() + ")";
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UnaryOperation unaryOperation) {
            return Objects.equals(unaryOperation.a, a);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return  Objects.hash(a, getClass());
    }
}