package expression.generic;

import expression.calculator.*;
import expression.exceptions.ParserException;

import java.util.Map;

public class GenericTabulator implements Tabulator {
    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws ParserException {
        var calculators
                = Map.of("i", new IntegerCalculator(), "d", new DoubleCalculator(), "bi", new BigIntegerCalculator(),
        "u", new UncheckedIntCalculator(), "s", new ShortCalculator(), "l", new LongCalculator());

        return new ArrayFiller().tabulate(calculators.get(mode), expression, x1, x2, y1, y2, z1, z2);
    }
}




