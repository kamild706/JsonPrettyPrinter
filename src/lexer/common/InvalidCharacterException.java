package lexer.common;

public class InvalidCharacterException extends RuntimeException {
    public InvalidCharacterException(char found, int index) {
        super(String.format("Invalid character '%s' at index %d", found, index));
    }
}
