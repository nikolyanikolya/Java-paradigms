package expression.exceptions;

import expression.*;
import expression.calculator.Calculator;
import expression.calculator.IntegerCalculator;
import expression.generic.GenericParser;
import expression.parser.BaseParser;
import expression.parser.ExpressionSource;
import expression.parser.StringSource;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;


public class ExpressionParser<T extends Number> implements TripleParser, GenericParser<T> {

    private static final Set<Character> VARIABLES = Set.of('x', 'y', 'z');

    @Override
    public TemplateExpression<Integer> parse(String expression) throws ParserException {
        return (new RunParser<Integer>(new StringSource(expression), new IntegerCalculator())).parse();
    }

    @Override
    public GenericExpression<T> parse(String expression, Calculator<T> calculator) throws ParserException {
        return (new RunParser<T>(new StringSource(expression), calculator)).parse();
    }

    private static class RunParser<T extends Number> extends BaseParser<T> {
        public RunParser(ExpressionSource source, Calculator<T> calculator) {
            super(source, calculator);
        }

        private final Map<String, Function<TemplateExpression<T>, UnaryOperation<T>>>
                UNARY_FUNCTION_LIST = Map.of("l0", LowerBitCount::new, "t0", UpperBitCount::new, "count", Count::new);

        public TemplateExpression<T> parse() throws ParserException {
            var result = minMaxParse();
            expect(END);
            return result;
        }

        private TemplateExpression<T> minMaxParse() throws ParserException {
            var before = addSubParse();
            while (true) {
                skipWhitespace();
                if (test("min")) {
                    checkForSymbolAfterFunctionName();
                    before = new Min<>(before, addSubParse());
                } else if (test("max")) {
                    checkForSymbolAfterFunctionName();
                    before = new Max<>(before, addSubParse());

                } else {
                    return before;
                }
            }
        }

        private TemplateExpression<T> addSubParse() throws ParserException {
            var before = mulDivParse();
            while (true) {
                skipWhitespace();
                if (test('+')) {
                    before = new CheckedAdd<>(before, mulDivParse());
                } else if (test('-')) {
                    before = new CheckedSubtract<>(before, mulDivParse());
                } else {
                    return before;
                }
            }
        }

        private TemplateExpression<T> mulDivParse() throws ParserException {
            var before = parenParse();
            while (true) {
                skipWhitespace();
                if (test('*')) {
                    before = new CheckedMultiply<>(before, parenParse());
                } else if (test('/')) {
                    before = new CheckedDivide<>(before, parenParse());
                } else {
                    return before;
                }
            }
        }

        private TemplateExpression<T> parenParse() throws ParserException {
            skipWhitespace();
            if (test('(')) {
                var ExprInParen = minMaxParse();
                expect(')');
                return ExprInParen;
            }
            return numParse();

        }


        private TemplateExpression<T> numParse() throws ParserException {
            if (test('-')) {
                if (between('0', '9')) {
                    return parseNumber(false);
                } else {
                    return new CheckedNegate<>(parenParse());
                }
            } else if (between('0', '9')) {
                return parseNumber(true);
            }
            return bitFunctionsParse();
        }

        private TemplateExpression<T> variableParse() throws ParserException {
            char ch = getChar();
            if (VARIABLES.contains(ch)) {
                nextChar();
                return new Variable<>(String.valueOf(ch));
            }
            throw error("Can`t parse expression");
        }

        private TemplateExpression<T> bitFunctionsParse() {
            for (var functionName : UNARY_FUNCTION_LIST.keySet()) {
                if (test(functionName)) {
                    if (isCorrectSymbolAfterFunctionName()) {
                        var digit = parenParse();
                        return UNARY_FUNCTION_LIST.get(functionName).apply(digit);
                    }
                    throw error("Invalid function name");
                }
            }
            return variableParse();
        }
    }
}
