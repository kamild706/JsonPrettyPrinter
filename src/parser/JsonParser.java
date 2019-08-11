package parser;

import lexer.JsonTokenType;
import lexer.common.Lexer;
import parser.common.Parser;
import parser.common.SyntaxException;

import static lexer.JsonTokenType.*;

public class JsonParser extends Parser {
    public JsonParser(Lexer input) {
        super(input);
    }

    private void json() {
        switch ((JsonTokenType) lookahead.getType()) {
            case LEFT_BRACE -> object();
            case LEFT_BRACKET -> list();
            default -> throw SyntaxException.of(lookahead.getType().toString(), LEFT_BRACE.toString(), LEFT_BRACKET.toString());
        }
    }

    private void object() {
        match(LEFT_BRACE);
        while (!lookahead.getType().equals(RIGHT_BRACE) && !lookahead.getType().equals(EOI))
            properties();
        match(RIGHT_BRACE);
    }

    private void properties() {
        property();
        while (lookahead.getType().equals(COMMA)) {
            match(COMMA);
            property();
        }
    }

    private void property() {
        key();
        match(COLON);
        value();
    }

    private void value() {
        switch ((JsonTokenType) lookahead.getType()) {
            case LEFT_BRACE, LEFT_BRACKET -> json();
            case NAME -> match(NAME);
            case NUMBER -> match(NUMBER);
            case BOOLEAN -> match(BOOLEAN);
            case NULL -> match(NULL);
            default -> throw SyntaxException.of(lookahead.getType().toString(), LEFT_BRACE.toString(), LEFT_BRACKET.toString(),
                    NAME.toString(), NUMBER.toString(), BOOLEAN.toString(), NULL.toString());
        }
    }

    private void key() {
        match(NAME);
    }

    private void list() {
        match(LEFT_BRACKET);
        while (!lookahead.getType().equals(RIGHT_BRACKET) && !lookahead.getType().equals(EOI))
            elements();
        match(RIGHT_BRACKET);
    }

    private void elements() {
        element();
        while (lookahead.getType().equals(COMMA)) {
            match(COMMA);
            element();
        }
    }

    private void element() {
        value();
    }

    @Override
    public void parse() {
        json();
    }
}
