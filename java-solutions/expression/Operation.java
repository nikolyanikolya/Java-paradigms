package expression;

public abstract class Operation <T extends Number> implements TemplateExpression<T>{
    protected abstract String getOperation();
}
