package gen;

import java.util.List;
import java.util.Random;

/**
 *
 */
public class RandomPolicy {

    Random rnd = new Random();

    public TokenString produce(RuleRegistry ruleRegistry, TokenString tokenString) {
        TokenString res = new TokenString();
        for (Token tk : tokenString.tokenList) {
            if (!tk.isTerminal()) {
                TokenString partialTokStr = this.produce(ruleRegistry, tk);
                res.add(partialTokStr);
            } else {
                assert tk.isTerminal();
                res.add(tk);
            }
        }
        return res;
    }

    public TokenString produce(RuleRegistry ruleRegistry, Token token) {
        final String name = token.getName();
        List<Rule> rules = ruleRegistry.getRulesByName(name);
        int which = rnd.nextInt(rules.size());
        Rule rule = rules.get(which);
        final TokenString rightSide = rule.getRightSide();
        return rightSide;
    }
    
}
