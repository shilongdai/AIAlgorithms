package net.viperfish.ai.propLogic;

import java.util.Objects;

public class CNFSymbol {

    private String literal;
    private boolean negate;

    public CNFSymbol(String literal, boolean negate) {
        this.literal = literal;
        this.negate = negate;
    }

    public String getLiteral() {
        return literal;
    }

    public boolean isNegate() {
        return negate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CNFSymbol cnfSymbol = (CNFSymbol) o;
        return negate == cnfSymbol.negate &&
                Objects.equals(literal, cnfSymbol.literal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(literal, negate);
    }
}
