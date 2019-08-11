package parser;

import lexer.common.Lexer;
import parser.common.Parser;
import parser.common.SyntaxException;

import static lexer.JsonTokenType.*;

public class JsonParser extends Parser {
    public JsonParser(Lexer input) {
        super(input);
    }

    private void json() {
        if (lookahead.getType().equals(LEFT_BRACE)) {
            object();
        } else if (lookahead.getType().equals(LEFT_BRACKET)) {
            list();
        } else {
            throw SyntaxException.of(lookahead.getType().toString(), LEFT_BRACE.toString(), LEFT_BRACKET.toString());
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
        if (lookahead.getType().equals(LEFT_BRACE) || lookahead.getType().equals(LEFT_BRACKET)) {
            json();
        } else if (lookahead.getType().equals(NAME)) {
            match(NAME);
        } else if (lookahead.getType().equals(NUMBER)) {
            match(NUMBER);
        } else if (lookahead.getType().equals(BOOLEAN)) {
            match(BOOLEAN);
        } else if (lookahead.getType().equals(NULL)) {
            match(NULL);
        } else {
            throw SyntaxException.of(lookahead.getType().toString(), LEFT_BRACE.toString(), LEFT_BRACKET.toString(),
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
