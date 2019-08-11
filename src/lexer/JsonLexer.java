package lexer;

import lexer.common.Lexer;
import lexer.common.Token;
import lexer.common.UnexpectedInputException;

import java.math.BigDecimal;

import static lexer.JsonToken.*;

public class JsonLexer extends Lexer {
    public JsonLexer(String input) {
        super(input.toCharArray());
    }

    @Override
    public Token nextToken() {
        while (currentChar != END_OF_INPUT) {
            if (isWhiteSpace(currentChar)) {
                skipWhiteSpaces();
                continue;
            }
            switch (currentChar) {
                case '[':
                    consume();
                    return LEFT_BRACKET;
                case ']':
                    consume();
                    return RIGHT_BRACKET;
                case '{':
                    consume();
                    return LEFT_BRACE;
                case '}':
                    consume();
                    return RIGHT_BRACE;
                case ':':
                    consume();
                    return COLON;
                case ',':
                    consume();
                    return COMMA;
                case '"':
                    return matchName();
                case 'n':
                    return matchNull();
                case 't':
                    return matchBoolean(true);
                case 'f':
                    return matchBoolean(false);
                case '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9':
                    return matchNumber();
                default:
                    throw new UnexpectedInputException(currentChar, index);
            }
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
