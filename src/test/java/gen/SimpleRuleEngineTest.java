package gen;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *
 */
class SimpleRuleEngineTest {

    @Test
    void parse() {
        
        final String ruleFileName = "gen/english-syntax.rules";

        SimpleRuleEngine engine = SimpleRuleEngine
                .parse(this.getClass().getClassLoader().getResourceAsStream(ruleFileName));
        assertNotNull(engine);

        Rule sentenceRule = engine.getRuleRegistry().getRuleByName("S");
        assertNotNull(sentenceRule);

        NonTerminal sentence = engine.getNonTerminal("S");
        assertNotNull(sentence);

        RandomPolicy policy = new RandomPolicy();

        TokenString tokenString = engine.tokenRegistry.of("S");  //= randomPolicy.produce( { engine.getNonTerminal("S") } );
        System.out.println(tokenString);

        int counter = 0;
        while(
                counter++ < 10 &&
                (tokenString = engine.produce(policy, tokenString))
                        .isNonTerminal()) {
            System.out.println(tokenString);
        }

    }
}