package parser;

import lexer.JsonTokenType;
import lexer.common.Lexer;
import parser.common.Parser;
import parser.common.SyntaxException;

import static lexer.JsonTokenType.*;
import static parser.common.SyntaxException.alternatives;

public class JsonParser extends Parser {
    public JsonParser(Lexer input) {
        super(input);
    }

    private void json() {
        switch ((JsonTokenType) lookahead.getType()) {
            case LEFT_BRACE -> object();
            case LEFT_BRACKET -> list();
            default -> throw new SyntaxException(alternatives(LEFT_BRACE, LEFT_BRACKET), lookahead.getType());
        }
    }

    private void object() {
        match(LEFT_BRACE);
        while (!lookahead.isOfType(RIGHT_BRACE) && !lookahead.isOfType(EOI))
            properties();
        match(RIGHT_BRACE);
    }

    private void properties() {
        property();
        while (lookahead.isOfType(COMMA)) {
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
            default -> throw new SyntaxException(
                    alternatives(LEFT_BRACE, LEFT_BRACKET, NAME, NUMBER, BOOLEAN, NULL), lookahead.getType());
        }
    }

    private void key() {
        match(NAME);
    }

    private void list() {
        match(LEFT_BRACKET);
        while (!lookahead.isOfType(RIGHT_BRACKET) && !lookahead.isOfType(EOI))
            elements();
        match(RIGHT_BRACKET);
    }

    private void elements() {
        element();
        while (lookahead.isOfType(COMMA)) {
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
