package src.lexer;

import lexer.*;
import lexer.common.InvalidCharacterException;
import lexer.common.Lexer;
import lexer.common.Token;
import lexer.common.UnexpectedInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static lexer.JsonToken.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

class JsonLexerTest {

    private List<Token> expectedTokens;

    @BeforeEach
    void setup() {
        expectedTokens = new ArrayList<>();
    }

    void tokenize(String input) {
        Lexer lexer = new JsonLexer(input);
        Token token;

        int index = 0;
        while ((token = lexer.nextToken()) != EOI) {
            Token expected = expectedTokens.get(index++);
            assertEquals(expected.getType(), token.getType());
            assertEquals(expected.getValue(), token.getValue());
        }
        assertEquals(expectedTokens.size(), index);
    }

    @Test
    void lexerTest1() {
        String input = "{}";
        expectedTokens.add(LEFT_BRACE);
        expectedTokens.add(RIGHT_BRACE);
        tokenize(input);
    }

    @Test
    void lexerTest2() {
        String input = "[{\"key\": \"value\"]}";
        expectedTokens.add(LEFT_BRACKET);
        expectedTokens.add(LEFT_BRACE);
        expectedTokens.add(nameToken("key"));
        expectedTokens.add(COLON);
        expectedTokens.add(nameToken("value"));
        expectedTokens.add(RIGHT_BRACKET);
        expectedTokens.add(RIGHT_BRACE);
        tokenize(input);
    }

    @Test
    void lexerTest3() {
        String input = "[\n" +
                "  {\n" +
                "    \"_id\": \"5d4ddc481029f03160658da7\",\n" +
                "    \"index\": 0.65,\n" +
                "    \"guid\": \"db8ae4c2-1a64-4efb-817e-98d3bf9f6125\",\n" +
                "    \"isActive\": false,\n" +
                "    \"balance\": \"$1,578.48\",\n" +
                "    \"picture\": \"http://placehold.it/32x32\",\n" +
                "    \"age\": 40,\n" +
                "    \"eyeColor\": \"blue\",\n" +
                "    \"name\": {\n" +
                "      \"first\": \"Rose\",\n" +
                "      \"last\": null\n" +
                "    }\n" +
                "}\n" +
                "]";

        expectedTokens.add(LEFT_BRACKET);
        expectedTokens.add(LEFT_BRACE);

        expectedTokens.add(nameToken("_id"));
        expectedTokens.add(COLON);
        expectedTokens.add(nameToken("5d4ddc481029f03160658da7"));
        expectedTokens.add(COMMA);

        expectedTokens.add(nameToken("index"));
        expectedTokens.add(COLON);
        expectedTokens.add(numberToken("0.65"));
        expectedTokens.add(COMMA);

        expectedTokens.add(nameToken("guid"));
        expectedTokens.add(COLON);
        expectedTokens.add(nameToken("db8ae4c2-1a64-4efb-817e-98d3bf9f6125"));
        expectedTokens.add(COMMA);

        expectedTokens.add(nameToken("isActive"));
        expectedTokens.add(COLON);
        expectedTokens.add(booleanToken("false"));
        expectedTokens.add(COMMA);

        expectedTokens.add(nameToken("balance"));
        expectedTokens.add(COLON);
        expectedTokens.add(nameToken("$1,578.48"));
        expectedTokens.add(COMMA);

        expectedTokens.add(nameToken("picture"));
        expectedTokens.add(COLON);
        expectedTokens.add(nameToken("http://placehold.it/32x32"));
        expectedTokens.add(COMMA);

        expectedTokens.add(nameToken("age"));
        expectedTokens.add(COLON);
        expectedTokens.add(numberToken("40"));
        expectedTokens.add(COMMA);

        expectedTokens.add(nameToken("eyeColor"));
        expectedTokens.add(COLON);
        expectedTokens.add(nameToken("blue"));
        expectedTokens.add(COMMA);

        expectedTokens.add(nameToken("name"));
        expectedTokens.add(COLON);
        expectedTokens.add(LEFT_BRACE);

        expectedTokens.add(nameToken("first"));
        expectedTokens.add(COLON);
        expectedTokens.add(nameToken("Rose"));
        expectedTokens.add(COMMA);

        expectedTokens.add(nameToken("last"));
        expectedTokens.add(COLON);
        expectedTokens.add(NULL);

        expectedTokens.add(RIGHT_BRACE);
        expectedTokens.add(RIGHT_BRACE);
        expectedTokens.add(RIGHT_BRACKET);

        tokenize(input);
    }

    @Test
    void lexerTest4() {
        String input = "43.4.";
        assertThrows(InvalidCharacterException.class, () -> {
            tokenize(input);
        });
    }

    @Test
    void lexerTest5() {
        String input = "falze";
        assertThrows(UnexpectedInputException.class, () -> {
            tokenize(input);
        });
    }
}
