package interpreter.model.exp;

public enum Relop {
    LT("<"),
    LE("<="),
    EQ("=="),
    NE("!="),
    GT(">"),
    GE(">=");

    private final String symbol;

    Relop(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}