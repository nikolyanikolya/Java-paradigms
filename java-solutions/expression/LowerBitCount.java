package expression;

public class LowerBitCount extends UnaryOperation{
    public LowerBitCount(TemplateExpression a) {
        super(a);
    }

    @Override
    protected String getOperation() {
        return "l0";
    }

    @Override
    protected int calculate(int x) {
        int digit = x;
        int cnt = 0;
        if (digit >= 0){
            while(digit > 0) {
                digit /= 2;
                cnt++;
            }
            return 32 - cnt;
        }
        return 0;
    }
}
