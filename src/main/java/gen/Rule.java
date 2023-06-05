package gen;

public class Rule {
    
    NonTerminal leftSide;
    TokenString rightSide;

    public Rule(NonTerminal leftSide, TokenString rightSide) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    public String getName() {
        return this.leftSide.getName();
    }
    
    public NonTerminal getLeftSide() {
        return leftSide;
    }

    public TokenString getRightSide() {
        return rightSide;
    }

    @Override
    public String toString() {
        return leftSide + " => " + rightSide;
    }
}
