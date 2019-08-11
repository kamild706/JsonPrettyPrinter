package lexer;

import lexer.common.Token;

import java.math.BigDecimal;

public class JsonToken {
    public static final Token LEFT_BRACKET = new Token(JsonTokenType.LEFT_BRACKET, "[");
    public static final Token RIGHT_BRACKET = new Token(JsonTokenType.RIGHT_BRACKET, "]");
    public static final Token LEFT_BRACE = new Token(JsonTokenType.LEFT_BRACE, "{");
    public static final Token RIGHT_BRACE = new Token(JsonTokenType.RIGHT_BRACE, "}");
    public static final Token COLON = new Token(JsonTokenType.COLON, ":");
    public static final Token COMMA = new Token(JsonTokenType.COMMA, ",");
    public static final Token NULL = new Token(JsonTokenType.NULL, null);
    public static final Token EOI = new Token(JsonTokenType.EOI, null);

    public static Token nameToken(String value) {
        return new Token(JsonTokenType.NAME, value);
    }

    public static Token numberToken(String value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return new Token(JsonTokenType.NUMBER, bigDecimal);
    }

    public static Token booleanToken(String value) {
        return new Token(JsonTokenType.BOOLEAN, Boolean.valueOf(value));
    }
}
