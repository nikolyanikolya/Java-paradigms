package expression.exceptions;


public class MissingParenException extends ParserException {
    public MissingParenException(String message, String processedPrefix, Integer pos) {
        super(message, processedPrefix, pos);
    }
}
