import lexer.JsonLexer;
import lexer.common.Lexer;
import parser.JsonParser;
import parser.common.Parser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*Scanner scanner = new Scanner(System.in);
        Lexer lexer;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            lexer = new JsonLexer(line.toCharArray());
            Token token;
            while ((token = lexer.nextToken()) != JsonToken.EOI) {
                System.out.println(token);
            }
            System.out.print("> ");
        }*/

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            StringBuilder input = new StringBuilder();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isEmpty()) break;
                input.append(line).append('\n');
            }
            Lexer lexer = new JsonLexer(input.toString());
            Parser parser = new JsonParser(lexer);
            parser.parse();
        }
    }
}
