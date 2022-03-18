package expression;

import java.util.Objects;

public class Variable implements TemplateExpression{
    protected final String var;
    public Variable(String var) {
        this.var = var;
    }

    public int evaluate (int x, int y, int z){
        return switch (var) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> 0 /*throw new IllegalArgumentException("Unsupported variable name")*/;
        };
    }

    @Override
    public String toString() {
        return var;
    }

    public int evaluate (int x){
        if (Objects.equals(var, "x")) {
            return x;
        }
        throw new IllegalArgumentException("Unsupported variable name");
    }

}
