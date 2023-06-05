package gen;

public class NonTerminal extends AbstractToken {

    String name;
    
    public NonTerminal(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    @Override
    public boolean isTerminal() {
        return false;
    }

}
