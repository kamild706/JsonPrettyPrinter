package lexer.common;

public abstract class Lexer {
    private char[] input;
    protected int index;
    protected Character currentChar;

    public Lexer(char[] input) {
        this.input = input;
        this.index = 0;
        this.currentChar = input[0];
    }

    protected void consume() {
        index++;
        if (index >= input.length)
            currentChar = null;
        else
            currentChar = input[index];
    }

    protected void match(char expected) {
        if (expected == currentChar)
            consume();
        else
            throw new UnexpectedInputException(expected, currentChar, index);
    }

    protected void match(String expected) {
        char[] chars = expected.toCharArray();
        int position = index;
        StringBuilder buffer = new StringBuilder();

        for (char aChar : chars) {
            if (aChar == currentChar) {
                buffer.append(aChar);
                consume();
            }
            else
                throw new UnexpectedInputException(expected, buffer.toString(), position);
        }
    }

    protected void skipWhiteSpaces() {
        while (isWhiteSpace(currentChar))
            consume();
    }

    protected boolean isWhiteSpace(char aChar) {
        return aChar == ' ' || aChar == '\n' || aChar == '\r' || aChar == '\t';
    }

    public abstract Token nextToken();
}
