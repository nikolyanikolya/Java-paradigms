package expression.exceptions;
import expression.TemplateExpression;


public class CheckedNegate extends expression.Negate  {

    public CheckedNegate(TemplateExpression a) {
        super(a);
    }
    @Override
    protected int calculate(int x) {
        return intCalculator.negate(x);
    }
}