package expression.parser;

import expression.exceptions.ParserException;

public interface ExpressionSource {
    boolean hasNext();

    Character next();

    ParserException error(final String message);

    int getPos();

    void setPos(int i);
}
