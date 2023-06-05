package gen;

/**
 *
 */
public class Terminal extends AbstractToken {

    String text;

    public Terminal(String text) {
        this.text = text;
    }

    @Override
    public String getName() {
        return this.text;
    }

    @Override
    public boolean isTerminal() {
        return true;
    }
}
