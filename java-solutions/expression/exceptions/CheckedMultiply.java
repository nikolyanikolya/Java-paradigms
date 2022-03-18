package expression.exceptions;
import expression.TemplateExpression;


public class CheckedMultiply extends expression.Multiply  {
    public CheckedMultiply(TemplateExpression a, TemplateExpression b) {
        super(a, b);
    }
    @Override
    protected int calculate(int x, int y) {
        return intCalculator.multiply(x, y);
    }

}