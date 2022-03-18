package expression;

public class Min extends BinaryOperation{
    public Min(TemplateExpression a, TemplateExpression b){
        super(a, b);
    }
    @Override
    @SuppressWarnings("ManualMinMaxCalculation")
    protected int calculate(int x, int y) {
        return x < y ? x : y;
    }

    @Override
    protected String getOperation() {
        return "min";
    }
}