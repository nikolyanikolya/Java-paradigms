package expression.generic;

import expression.GenericExpression;
import expression.calculator.Calculator;
import expression.exceptions.EvaluateException;
import expression.parser.ExpressionParser;
import expression.exceptions.ParserException;

public class ArrayFiller {
    public <T extends Number> Object[][][] tabulate(Calculator<T> calculator,
                                                    String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws ParserException {
        ExpressionParser<T> parser = new ExpressionParser<>();
        GenericExpression<T> expr = parser.parse(expression, calculator);
        final int x = x2 - x1 + 1;
        final int y = y2 - y1 + 1;
        final int z = z2 - z1 + 1;
        Object[][][] answer = new Object[x][y][z];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < z; k++) {
                    if (expr != null) {
                        try {
                            answer[i][j][k] = expr.evaluate(calculator.castFromInt(x1 + i),
                                    calculator.castFromInt(y1 + j), calculator.castFromInt(z1 + k), calculator);
                        } catch (EvaluateException e) {
                            answer[i][j][k] = null;
                        }
                    }

                }
            }
        }
        return answer;
    }
}
