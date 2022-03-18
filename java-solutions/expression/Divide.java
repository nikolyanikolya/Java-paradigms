package expression;


public class Divide extends BinaryOperation {
    public Divide(TemplateExpression a, TemplateExpression b) {
        super(a, b);
    }

    @Override
    protected int calculate(int x, int y) {
        return x / y;
    }

    @Override
    protected String getOperation() {
        return "/";
    }
}
