package gen;

public class QuantifiedToken extends AbstractToken {

    static enum Quantifier {
        EXACTLY_ONCE,
        ZERO_OR_ONCE
    }

    Token token;
    Quantifier quantifier;

    public QuantifiedToken(Token token, Quantifier quantifier) {
        this.token = token;
        this.quantifier = quantifier;
    }

    public QuantifiedToken(Token token) {
        this(token, Quantifier.EXACTLY_ONCE);
    }

    @Override
    public String getName() {
        return this.token.getName();
    }

    @Override
    public String toString() {
        if (this.quantifier == Quantifier.ZERO_OR_ONCE)
            return "(" + this.getName() + ")";
        else
            return super.toString();
    }

    @Override
    public boolean isTerminal() {
        return this.token.isTerminal();
    }

}
