package lexer;

import lexer.common.TokenType;

public enum JsonTokenType implements TokenType {
    LEFT_BRACKET,
    RIGHT_BRACKET,
    LEFT_BRACE,
    RIGHT_BRACE,
    COLON,
    COMMA,
    NAME,
    NUMBER,
    BOOLEAN,
    NULL,
    EOI
}
