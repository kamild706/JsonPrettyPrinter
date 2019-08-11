package lexer.common;

public class UnexpectedInputException extends RuntimeException {
    public UnexpectedInputException(char expecting, char found, int index) {
        this(String.valueOf(expecting), String.valueOf(found), index);
    }

    public UnexpectedInputException(String expecting, String found, int index) {
        super(String.format("Expecting '%s', but found '%s' at index %d", expecting, found, index));
    }

    public UnexpectedInputException(char found, int index) {
        super(String.format("Unexpected character '%c' at index %d", found, index));
    }
}
