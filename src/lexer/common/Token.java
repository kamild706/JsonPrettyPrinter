package lexer.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Token {
    private final TokenType type;
    private final Object value;
}
