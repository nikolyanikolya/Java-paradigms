package expression;

public class UpperBitCount extends UnaryOperation{

    public UpperBitCount(TemplateExpression a) {
        super(a);
    }

    @Override
    protected String getOperation() {
        return "t0";
    }

    @Override
    protected int calculate(int x) {
        int digit = x;
        int cnt = 0;
        int parity = 0;
        if (digit == 0){
            return 32;
        }
        if(digit < 0){
            parity = 1;
            digit = -(digit + 1);
        }
        while(digit % 2 == parity) {
            digit /= 2;
            cnt++;
        }
        return cnt;
    }
}
