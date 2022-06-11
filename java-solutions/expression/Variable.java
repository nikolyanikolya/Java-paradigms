package expression;

import expression.calculator.Calculator;

import java.util.Objects;

public class Variable<T extends Number> implements TemplateExpression<T> {
    protected final String var;

    public Variable(String var) {
        this.var = var;
    }

    public int evaluate(int x, int y, int z) {
        return switch (var) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> 0;
        };
    }

    @Override
    public String toString() {
        return var;
    }

    public int evaluate(int x) {
        if (Objects.equals(var, "x")) {
            return x;
        }
        throw new IllegalArgumentException("Unsupported variable name");
    }

    @Override
    public T evaluate(T x, T y, T z, Calculator<T> calculator) {
        return switch (var) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> null;
        };
    }
}
