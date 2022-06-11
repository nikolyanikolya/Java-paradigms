package expression.generic;

import expression.GenericExpression;
import expression.calculator.Calculator;
import expression.exceptions.ParserException;

public interface GenericParser<T extends Number>{
    GenericExpression<T> parse(String expression, Calculator<T> calculator) throws ParserException;
}
