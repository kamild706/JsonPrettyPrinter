package lexer;

import lexer.common.Lexer;
import lexer.common.Token;
import lexer.common.UnexpectedInputException;

import java.math.BigDecimal;

import static lexer.JsonToken.*;

public class JsonLexer extends Lexer {
    public JsonLexer(char[] input) {
        super(input);
    }

    public JsonLexer(String input) {
        this(input.toCharArray());
    }

    @Override
    public Token nextToken() {
        while (currentChar != END_OF_INPUT) {
            if (isWhiteSpace(currentChar)) {
                skipWhiteSpaces();
                continue;
            }
            if (currentChar == '[') {
                consume();
                return LEFT_BRACKET;
            }
            if (currentChar == ']') {
                consume();
                return RIGHT_BRACKET;
            }
            if (currentChar == '{') {
                consume();
                return LEFT_BRACE;
            }
            if (currentChar == '}') {
                consume();
                return RIGHT_BRACE;
            }
            if (currentChar == ':') {
                consume();
                return COLON;
            }
            if (currentChar == ',') {
                consume();
                return COMMA;
            }
            if (currentChar == '"') {
                return matchName();
            }
            if (currentChar == 'n') {
                return matchNull();
            }
            if (currentChar == 't') {
                return matchBoolean(true);
            }
            if (currentChar == 'f') {
                return matchBoolean(false);
            }
            if (currentChar == '-' || currentChar >= '0' && currentChar <= '9') {
                return matchNumber();
            }
            throw new UnexpectedInputException(currentChar, index);
        }
        return EOI;
    }

    private Token matchNumber() {
        boolean isDecimalSeparatorFound = false;
        StringBuilder sb = new StringBuilder();
        sb.append(currentChar);
        consume();

        while (currentChar != END_OF_INPUT) {
            if (currentChar >= '0' && currentChar <= '9') {
                sb.append(currentChar);
            } else if (currentChar == '.') {
                if (isDecimalSeparatorFound) {
                    throw new UnexpectedInputException(currentChar, index);
                } else {
                    sb.append(currentChar);
                    isDecimalSeparatorFound = true;
                }
            } else {
                break;
            }

            consume();
        }

        String numString = sb.toString();
        if (numString.endsWith(".") || numString.endsWith("-")) {
            throw new UnexpectedInputException(sb.charAt(sb.length() - 1), index - 1);
        }

        BigDecimal number = new BigDecimal(numString);
        return new Token(JsonTokenType.NUMBER, number);
    }

    private Token matchBoolean(boolean bool) {
        if (bool) {
            match("true");
            return TRUE;
        } else {
            match("false");
            return FALSE;
        }
    }

    private Token matchNull() {
        match("null");
        return NULL;
    }

    private Token matchName() {
        consume();
        StringBuilder sb = new StringBuilder();
        while (currentChar != END_OF_INPUT && currentChar != '"') {
            sb.append(currentChar);
            consume();
        }
        consume();
        return new Token(JsonTokenType.NAME, sb.toString());
    }
}
