import lexer.JsonLexer;
import lexer.JsonToken;
import lexer.common.Lexer;
import lexer.common.Token;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Lexer lexer;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            lexer = new JsonLexer(line.toCharArray());
            Token token;
            while ((token = lexer.nextToken()) != JsonToken.EOI) {
                System.out.println(token);
            }
            System.out.print("> ");
        }


    }
}
