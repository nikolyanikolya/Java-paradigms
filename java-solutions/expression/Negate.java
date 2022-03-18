package expression;


public class Negate extends UnaryOperation{
    public Negate(TemplateExpression a){
        super(a);
    }
    @Override
    protected int calculate(int x) {
        return -x;
    }


    @Override
    protected String getOperation() {
        return "-";
    }
}
