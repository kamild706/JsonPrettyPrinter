package parser;

import lexer.common.Lexer;
import lexer.common.Token;

import java.util.List;

class FakeJsonLexer extends Lexer {
    private final List<Token> tokens;
    private int index = 0;

    FakeJsonLexer(List<Token> tokens) {
        super(new char[]{'a'});
        this.tokens = tokens;
    }

    @Override
    public Token nextToken() {
        Token token = tokens.get(index);
        if (index < tokens.size() - 1) index++;
        return token;
    }
}
