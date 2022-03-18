package expression;

public class Max extends BinaryOperation{
    public Max(TemplateExpression a, TemplateExpression b){
        super(a, b);
    }
    @Override
    @SuppressWarnings("ManualMinMaxCalculation")
    protected int calculate(int x, int y) {
        return x < y ? y : x;
    }

    @Override
    protected String getOperation() {
        return "max";
    }
}