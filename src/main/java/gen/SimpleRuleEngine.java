package gen;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

/**
 * acum nu mai am ce să-i zic.
 */
public class SimpleRuleEngine {


    TokenRegistry tokenRegistry = new TokenRegistry();
    RuleRegistry ruleRegistry = new RuleRegistry();
    
    public SimpleRuleEngine() {}

    public static SimpleRuleEngine parse(InputStream inputStream) {
        final SimpleRuleEngine simpleRuleEngine = new SimpleRuleEngine();
        simpleRuleEngine.parseFrom(inputStream);
        return simpleRuleEngine;
    }

    protected void parseFrom(InputStream inputStream) {

        try {
            List<Rule> rules = new ArrayList<>();

            final List<String> lines = IOUtils.readLines(inputStream, Charset.forName("UTF-8"));
            int counter = 0;
            for (String line: lines) {
                
                counter++;

                final String[] split = line.split("->");
                assert split.length == 2;

                final String leftSideStr = split[0].trim();
                final String rightSideStr = split[1].trim();

//                System.out.println(leftSideStr + " : " + rightSideStr);

                {

                    assert leftSideStr != null;
                    assert isUppercase(leftSideStr);

                    NonTerminal leftSideNonTerminal = this.getNonTerminal(leftSideStr);
                    if (leftSideNonTerminal == null) {
                        leftSideNonTerminal = new NonTerminal(leftSideStr);
                        this.tokenRegistry.add(leftSideNonTerminal);
                    }


                    TokenString rightSide = parseRightSide(counter, rightSideStr);
                    
//                    Rule r = this.getRuleByName(leftSideStr);
//                    if (r != null) {
//                        // there shouldn't be any pre-existing rule for this
//                        reportError(counter, "duplicate rule for [" + leftSideStr + "]");
//                    }

                    Rule r = new Rule(leftSideNonTerminal, rightSide);
                    this.addRule(r);
                    
                    System.out.println(r);

                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private TokenString parseRightSide(int lineNr, String rightSideStr) {
        TokenString tokenString = new TokenString();
        String[] tkns = rightSideStr.split("[\\s\\,]+");
        for (String tkn: tkns) {
            boolean isOptional = false;

            assert !tkn.contains(" ");

            if (tkn.startsWith("(") && tkn.endsWith(")")) {
                // optional
                isOptional = true;
                tkn = tkn.substring(1, tkn.length() - 1);
                assert tkn.length() > 0;
            }

            Token token = this.tokenRegistry.getToken(tkn);
            if (token == null) {
                // new 'unknown' token
                if (isUppercase(tkn)) {
                    token = new NonTerminal(tkn);
                } else if (isLowercase(tkn)) {
                    token = new Terminal(tkn);
                } else {
                    reportError(lineNr, "don't know what kind of token is [" + tkn + "]");
                }
            }

            this.tokenRegistry.add(token);
            if (isOptional) {
                tokenString.add(new QuantifiedToken(token, QuantifiedToken.Quantifier.ZERO_OR_ONCE));
            } else {
                tokenString.add(token);
            }
        }

        return tokenString;
    }

    private static boolean isLowercase(String str){
        char[] charArray = str.toCharArray();
        for(int i=0; i < charArray.length; i++){
            if( !Character.isLowerCase( charArray[i] ))
                return false;
        }
        return true;
    }

    private static boolean isUppercase(String str){
        char[] charArray = str.toCharArray();
        for(int i=0; i < charArray.length; i++){
            if( !Character.isUpperCase( charArray[i] ))
                return false;
        }
        return true;
    }
    
    private void addRule(Rule r) {
        this.ruleRegistry.add(r);
    }

    private void reportError(int line, String message) {
        throw new IllegalStateException(String.format("line %d: %s", line, message));
    }

//    public Rule getRuleByName(String name) {
//        return this.ruleRegistry.get(name);
//    }

    public NonTerminal getNonTerminal(String name) {
        return this.tokenRegistry.getNonTerminal(name);
    }

    public TokenRegistry getTokenRegistry() {
        return tokenRegistry;
    }

    public RuleRegistry getRuleRegistry() {
        return ruleRegistry;
    }

    public TokenString produce(RandomPolicy policy, TokenString tokenString) {
        return policy.produce(this.ruleRegistry, tokenString);
    }
}
