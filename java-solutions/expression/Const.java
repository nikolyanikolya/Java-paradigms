package expression;


public class Const implements TemplateExpression{
    private final Integer value;

    public Const(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return value;
    }
    @Override
    public int evaluate(int x) {
        return evaluate(x, 0, 0);
    }


}
