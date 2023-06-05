package gen;

/**
 *
 */
public abstract class AbstractToken implements Token {

    @Override
    public String toString() {
        return getName();
    }

    public boolean isNonTerminal() {
        return ! this.isTerminal();
    }
}
