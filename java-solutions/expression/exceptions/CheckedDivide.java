package expression.exceptions;
import expression.TemplateExpression;

public class CheckedDivide extends expression.Divide{

    public CheckedDivide(TemplateExpression a, TemplateExpression b) {
        super(a, b);
    }
    @Override
    protected int calculate(int x, int y) {
        return intCalculator.divide(x, y);
    }
}
