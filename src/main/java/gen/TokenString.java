package gen;


import java.util.*;
import java.util.stream.Collectors;

/**
 * Just a list of Tokens, might be useful as production generates ever-growing lists of Tokens.
 */
public class TokenString {

    Map<String, Token> tokens = new HashMap<>();
    List<Token> tokenList = new ArrayList<>(); // order is important

    public TokenString(List<Token> tokens) {
        this.tokenList = tokens;
        tokens.stream().forEach(tk -> this.tokens.put(tk.getName(), tk));
    }

    public TokenString(Token[] tokens) {
        this(Arrays.asList(tokens));
    }

    public TokenString() {}

    /**
     * true if has any NonTerminal  (so the generation may continue)
     */
    public boolean isNonTerminal() {
        return tokens.values().stream().anyMatch(t -> !t.isTerminal());
    }

    public void add(Token token) {
        this.tokens.put(token.getName(), token);
        this.tokenList.add(token);
    }

    public void add(TokenString tokenString) {
        for (Token tk : tokenString.tokenList) {
            this.add(tk);
        }
    }

    @Override
    public String toString() {
        final List<String> collect = this.tokenList.stream().map(it -> it.toString()).collect(Collectors.toList());
        return String.join(" ", collect);
    }
}
