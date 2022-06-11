package expression;


import expression.calculator.Calculator;

public class Const<T extends Number> implements TemplateExpression<T> {
    private T value;
    public Const(T value) {
        this.value = value;
    }
    public Const(int value){ // for compatibility with HW1
    }
    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return (int) value;
    }

    @Override
    public int evaluate(int x) {
        return evaluate(x, 0, 0);
    }


    @Override
    public T evaluate(T x, T y, T z, Calculator<T> calculator) {
        return value;
    }
}
