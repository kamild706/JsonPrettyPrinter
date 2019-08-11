package parser.common;

import lexer.common.TokenType;

import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class SyntaxException extends RuntimeException {
    public SyntaxException(String expecting, String found) {
        super(String.format("Expecting '%s', found '%s'", expecting, found));
    }

    public SyntaxException(String expecting, TokenType found) {
        this(expecting, found.toString());
    }

    public static String alternatives(TokenType... alternatives) {
        return Stream.of(alternatives)
                .map(Object::toString)
                .collect(joining(" or "));
    }
}
