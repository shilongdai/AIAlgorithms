package net.viperfish.ai.csp;

import java.util.Objects;

public abstract class Constraint {

    private String src;
    private String dest;
    private String name;

    public Constraint(String src, String dest, String name) {
        this.src = src;
        this.dest = dest;
        this.name = name;
    }

    public String getSrc() {
        return src;
    }

    public String getDest() {
        return dest;
    }

    public abstract boolean validate(ConstraintProblem csp);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Constraint that = (Constraint) o;
        return Objects.equals(src, that.src) &&
                Objects.equals(dest, that.dest) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(src, dest, name);
    }
}
