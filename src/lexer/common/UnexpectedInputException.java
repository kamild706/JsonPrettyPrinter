package lexer.common;

public class UnexpectedInputException extends RuntimeException {
    UnexpectedInputException(char expecting, char found, int index) {
        this(String.valueOf(expecting), String.valueOf(found), index);
    }

    UnexpectedInputException(String expecting, String found, int index) {
        super(String.format("Expecting '%s', but found '%s' at index %d", expecting, found, index));
    }
}
