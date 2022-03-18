package expression.exceptions;
import expression.TemplateExpression;



public class CheckedSubtract extends expression.Subtract {

    public CheckedSubtract(TemplateExpression a, TemplateExpression b) {
        super(a, b);
    }
    @Override
    protected int calculate(int x, int y) {
        return intCalculator.subtract(x, y);
    }


}