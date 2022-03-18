package expression.exceptions;

public class ParserException extends RuntimeException{
    private final String prefix;
    private final Integer pos;
    public ParserException(String message, String processedPrefix, Integer pos){
        super("Character on pos " + pos.toString() + " in " + processedPrefix + " " + message);
        this.prefix = processedPrefix;
        this.pos = pos;
    }
    public String getPrefix() {
        return prefix;
    }

    public Integer getPos() {
        return pos;
    }

}
