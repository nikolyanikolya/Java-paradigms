package expression;

public class Multiply extends BinaryOperation{
    public Multiply(TemplateExpression a, TemplateExpression b){
        super(a, b);
    }
    @Override
    protected int calculate(int x, int y) {
        return x * y;
    }

    @Override
    protected String getOperation() {
        return "*";
    }
}
