package expression;

import expression.calculator.Calculator;
import expression.calculator.IntegerCalculator;

import java.util.Objects;

public abstract class BinaryOperation<T extends Number> extends Operation<T> {
    protected final Calculator<Integer> intCalculator = new IntegerCalculator();
    private final TemplateExpression<T> a;
    private final TemplateExpression<T> b;

    public BinaryOperation(TemplateExpression<T> a, TemplateExpression<T> b) {
        this.a = a;
        this.b = b;
    }

    protected abstract int calculate(int x, int y);

    protected abstract T calculate(T x, T y, Calculator<T> calculator);

    @Override
    public int evaluate(int value) {
        return calculate(a.evaluate(value), b.evaluate(value));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return calculate(a.evaluate(x, y, z), b.evaluate(x, y, z));
    }

    @Override
    public T evaluate(T x, T y, T z, Calculator<T> calculator){
        return calculate(a.evaluate(x, y, z, calculator), b.evaluate(x, y, z, calculator), calculator);
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
        return Objects.hash(a, b, getClass());
    }

}