package expression.parser;

import expression.exceptions.ParserException;

public class StringSource implements ExpressionSource {
    private final String string;
    private int pos;

    public StringSource(final String string) {
        this.string = string;
    }
    @Override
    public boolean hasNext() {
        return pos < string.length();
    }

    @Override
    public Character next() {
        return string.charAt(pos++);
    }

    @Override
    public ParserException error(String message) {
        return new ParserException(message, string.substring(0, pos), pos);
    }
    @Override
    public int getPos() {
        return pos;
    }
    @Override
    public void setPos(int i) {
        if(0 > i ||  i > string.length()){
            throw new AssertionError("Illegal index for setting");
        }
        pos = i;
    }
}
