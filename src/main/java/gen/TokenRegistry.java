package gen;

import java.util.*;

public class TokenRegistry {
    
    Map<String, Token> tokens = new HashMap<>();
    List<Token> tokenList = new ArrayList<>();

    public NonTerminal getNonTerminal(String name) {
        return (NonTerminal) this.getToken(name);
    }

    public Token getToken(String name) {
        if (!(this.tokens.containsKey(name)))
            return null;

        final Token token = this.tokens.get(name);
//        if (! (token instanceof NonTerminal))
//            return null;

        return token;
    }

    public TokenString of(String... terms) {
        TokenString ts = new TokenString();
        for (String term: terms) {
            Token token = this.getToken(term);
            ts.add(token);
        }
        return ts;
    }

    public void add(Token nt) {
        this.tokens.put(nt.getName(), nt);
        this.tokenList.add(nt);
    }
}
