package parser.common;

import lexer.common.Lexer;
import lexer.common.Token;
import lexer.common.TokenType;

public abstract class Parser {
    private final Lexer input;
    protected Token lookahead;

    public Parser(Lexer input) {
        this.input = input;
        lookahead = input.nextToken();
    }

    protected void consume() {
        lookahead = input.nextToken();
    }

    protected void match(TokenType tokenType) {
        if (lookahead.getType().equals(tokenType))
            consume();
        else
            throw new SyntaxException(tokenType.toString(), lookahead.getValue().toString());
    }

    public abstract void parse();
}
