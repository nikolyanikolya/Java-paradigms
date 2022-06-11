package expression.calculator;

public interface Calculator<T extends Number> {
    T add(T a, T b);

    T multiply(T a, T b);

    T divide(T a, T b);

    T subtract(T a, T b);

    T negate(T a);

    T min(T a, T b);

    T max(T a, T b);

    T parseString(String s);

    T lowerBit(T a);

    T upperBit(T a);

    T castFromInt(int a);

    T countBit(T a);
}
