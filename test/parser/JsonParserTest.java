package parser;

import lexer.JsonToken;
import lexer.common.Lexer;
import lexer.common.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.common.Parser;
import parser.common.SyntaxException;

import java.util.ArrayList;
import java.util.List;

import static lexer.JsonToken.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonParserTest {

    private List<Token> tokens;

    @BeforeEach
    void setup() {
        tokens = new ArrayList<>();
    }

    void parse() {
        tokens.add(EOI);
        Lexer lexer = new FakeJsonLexer(tokens);
        Parser parser = new JsonParser(lexer);
        parser.parse();
    }

    @Test
    void test1() {
        tokens.add(LEFT_BRACKET);
        tokens.add(JsonToken.RIGHT_BRACKET);
        assertDoesNotThrow(this::parse);
    }

    @Test
    void test2() {
        tokens.add(LEFT_BRACKET);
        tokens.add(LEFT_BRACKET);
        tokens.add(RIGHT_BRACKET);
        tokens.add(RIGHT_BRACKET);
        assertDoesNotThrow(this::parse);
    }

    @Test
    void test3() {
        tokens.add(LEFT_BRACKET);
        tokens.add(LEFT_BRACKET);
        tokens.add(LEFT_BRACE);
        tokens.add(RIGHT_BRACE);
        tokens.add(RIGHT_BRACKET);
        tokens.add(RIGHT_BRACKET);
        assertDoesNotThrow(this::parse);
    }

    @Test
    void test4() {
        tokens.add(LEFT_BRACKET);
        tokens.add(RIGHT_BRACKET);
        assertDoesNotThrow(this::parse);
    }

    @Test
    void test5() {
        tokens.add(LEFT_BRACE);
        tokens.add(nameToken("key"));
        tokens.add(COLON);
        tokens.add(nameToken("value"));
        tokens.add(RIGHT_BRACE);
        assertDoesNotThrow(this::parse);
    }

    @Test
    void test6() {
        tokens.add(LEFT_BRACKET);
        tokens.add(LEFT_BRACE);

        tokens.add(nameToken("_id"));
        tokens.add(COLON);
        tokens.add(nameToken("5d4ddc481029f03160658da7"));
        tokens.add(COMMA);

        tokens.add(nameToken("index"));
        tokens.add(COLON);
        tokens.add(numberToken("0.65"));
        tokens.add(COMMA);

        tokens.add(nameToken("guid"));
        tokens.add(COLON);
        tokens.add(nameToken("db8ae4c2-1a64-4efb-817e-98d3bf9f6125"));
        tokens.add(COMMA);

        tokens.add(nameToken("isActive"));
        tokens.add(COLON);
        tokens.add(FALSE);
        tokens.add(COMMA);

        tokens.add(nameToken("balance"));
        tokens.add(COLON);
        tokens.add(nameToken("$1,578.48"));
        tokens.add(COMMA);

        tokens.add(nameToken("picture"));
        tokens.add(COLON);
        tokens.add(nameToken("http://placehold.it/32x32"));
        tokens.add(COMMA);

        tokens.add(nameToken("age"));
        tokens.add(COLON);
        tokens.add(numberToken("40"));
        tokens.add(COMMA);

        tokens.add(nameToken("eyeColor"));
        tokens.add(COLON);
        tokens.add(nameToken("blue"));
        tokens.add(COMMA);

        tokens.add(nameToken("name"));
        tokens.add(COLON);
        tokens.add(LEFT_BRACE);

        tokens.add(nameToken("first"));
        tokens.add(COLON);
        tokens.add(nameToken("Rose"));
        tokens.add(COMMA);

        tokens.add(nameToken("last"));
        tokens.add(COLON);
        tokens.add(NULL);

        tokens.add(RIGHT_BRACE);
        tokens.add(RIGHT_BRACE);
        tokens.add(RIGHT_BRACKET);

        assertDoesNotThrow(this::parse);
    }

    @Test
    void test7() {
        tokens.add(LEFT_BRACE);
        assertThrows(SyntaxException.class, this::parse);
    }

    @Test
    void test8() {
        tokens.add(LEFT_BRACE);
        tokens.add(LEFT_BRACE);
        assertThrows(SyntaxException.class, this::parse);
    }

    @Test
    void test9() {
        tokens.add(LEFT_BRACE);
        tokens.add(nameToken("key"));
        tokens.add(numberToken("10"));
        tokens.add(RIGHT_BRACE);
        assertThrows(SyntaxException.class, this::parse);
    }

    @Test
    void test10() {
        tokens.add(LEFT_BRACKET);
        tokens.add(LEFT_BRACKET);
        tokens.add(RIGHT_BRACKET);
        tokens.add(COLON);
        tokens.add(LEFT_BRACE);
        tokens.add(RIGHT_BRACE);
        tokens.add(RIGHT_BRACKET);
        assertThrows(SyntaxException.class, this::parse);
    }

    @Test
    void test11() {
        tokens.add(LEFT_BRACE);
        tokens.add(LEFT_BRACE);
        tokens.add(RIGHT_BRACE);
        tokens.add(RIGHT_BRACE);
        assertThrows(SyntaxException.class, this::parse);
    }

    @Test
    void test12() {
        tokens.add(LEFT_BRACKET);
        tokens.add(LEFT_BRACKET);
        tokens.add(RIGHT_BRACKET);
        tokens.add(COMMA);
        tokens.add(LEFT_BRACE);
        tokens.add(RIGHT_BRACE);
        tokens.add(RIGHT_BRACKET);
        assertDoesNotThrow(this::parse);
    }

    @Test
    void test13() {
        tokens.add(LEFT_BRACKET);
        tokens.add(nameToken("key"));
        tokens.add(COLON);
        tokens.add(nameToken("value"));
        tokens.add(RIGHT_BRACKET);
        assertThrows(SyntaxException.class, this::parse);
    }

    @Test
    void test14() {
        tokens.add(LEFT_BRACKET);
        tokens.add(NULL);
        tokens.add(RIGHT_BRACKET);
        assertDoesNotThrow(this::parse);
    }

    @Test
    void test15() {
        tokens.add(LEFT_BRACKET);
        tokens.add(numberToken("54"));
        tokens.add(COMMA);
        tokens.add(FALSE);
        tokens.add(COMMA);
        tokens.add(NULL);
        tokens.add(COMMA);
        tokens.add(nameToken("hello"));
        tokens.add(RIGHT_BRACKET);
        assertDoesNotThrow(this::parse);
    }
}
