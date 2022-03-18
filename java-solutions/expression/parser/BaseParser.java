package expression.parser;

import expression.Const;

import expression.exceptions.MissingParenException;
import expression.exceptions.ParserException;


import static java.lang.Character.isWhitespace;

public abstract class BaseParser {
    protected static final char END = '\0';
    private final ExpressionSource source;
    private char ch;

    protected char getChar() {
        return ch;
    }
    protected BaseParser(final ExpressionSource source) {
        this.source = source;
        nextChar();
    }

    protected void nextChar() {
        ch = source.hasNext() ? source.next() : END;
    }

    protected boolean test(final char expected) {
        if (ch == expected) {
            nextChar();
            return true;
        }
        return false;
    }
    protected boolean test(final String expected){
        char ch0 = ch;
        int pos0 = source.getPos();
        for (int i = 0; i < expected.length(); i++){
            if(!test(expected.charAt(i))){
                ch = ch0;
                source.setPos(pos0);
                return false;
            }
        }
        return true;
    }
    protected void expect(final char c) throws ParserException {
        if (ch != c) {
            if (c == ')') {
                ParserException e = error("Unopened parenthesis");
                throw new MissingParenException(e.getMessage(), e.getPrefix(), e.getPos());
            } else if (ch == ')') {
                ParserException e = error("Unclosed parenthesis");
                throw new MissingParenException(e.getMessage(), e.getPrefix(), e.getPos());
            }
            String expect = c == END ? "end of expression" : "'" + c + "'";
            throw error("Expected " + expect + ", found '" + ch + "'");
        }
        nextChar();
    }


    protected Const parseNumber(boolean positive) throws ParserException {
        final StringBuilder sb = new StringBuilder(positive ? "" : "-");
        copyInteger(sb);

        try {
            return new Const(Integer.parseInt(sb.toString()));
        } catch (NumberFormatException e) {
            throw error("Const integer overflow: " + sb);
        }
    }
    protected void skipWhitespace() {
        while (Character.isWhitespace(ch)) {
            nextChar();
        }
    }

    protected ParserException error(final String message) {
        return source.error(message);
    }

    protected boolean between(final char from, final char to) {
        return from <= ch && ch <= to;
    }
    protected boolean isCorrectSymbolAfterFunctionName(){
        return isWhitespace(ch) || ch == '(' || ch == '-';
    }
    protected void checkForSymbolAfterFunctionName(){
        if(!isCorrectSymbolAfterFunctionName()){
            throw error("Invalid function name");
        }
    }
    private void copyInteger(final StringBuilder sb) throws ParserException {
        if (test('0')) {
            sb.append('0');
        } else if (between('1', '9')) {
            copyDigits(sb);
        } else {
            ParserException e = error("Const integer overflow");
            throw new MissingParenException(e.getMessage(), e.getPrefix(), e.getPos());
        }
    }
    private void copyDigits(final StringBuilder sb) {
        while (between('0', '9')) {
            sb.append(ch);
            nextChar();
        }
    }
}