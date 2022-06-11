package expression;

import expression.calculator.Calculator;

public interface GenericExpression <T extends Number>{
    T evaluate (T x, T y, T z, Calculator<T> calculator);
}
