package parser.common;

public class SyntaxException extends RuntimeException {
    public SyntaxException(String expecting, String found) {
        super(String.format("Expecting '%s' token but found '%s'", expecting, found));
    }

    public static SyntaxException of(String found, String... expected) {
        StringBuilder sb = new StringBuilder();
        sb.append(expected[0]);

        for (int i = 1; i < expected.length; i++) {
            sb.append(" or ");
            sb.append(expected[i]);
        }

        return new SyntaxException(sb.toString(), found);
    }
}
