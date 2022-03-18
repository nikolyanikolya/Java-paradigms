package expression.exceptions;
import expression.TemplateExpression;


public class CheckedAdd extends expression.Add {
    public CheckedAdd(TemplateExpression a, TemplateExpression b) {
        super(a, b);
    }
    @Override
    protected int calculate(int x, int y) {
        return intCalculator.add(x, y);
    }

}
