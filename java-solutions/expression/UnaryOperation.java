package expression;


import expression.calculator.Calculator;
import expression.calculator.IntegerCalculator;

import java.util.Objects;

public abstract class UnaryOperation<T extends Number> extends Operation<T> {
    protected final Calculator<Integer> intCalculator = new IntegerCalculator();
    protected final TemplateExpression<T> a;

    protected UnaryOperation(TemplateExpression<T> a) {
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

    @Override
    public T evaluate(T x, T y, T z, Calculator<T> calculator){
        return calculate(a.evaluate(x, y, z, calculator), calculator);
    }

    protected abstract int calculate(int x);

    protected abstract T calculate(T x, Calculator<T> calculator);

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
        return Objects.hash(a, getClass());
    }
}