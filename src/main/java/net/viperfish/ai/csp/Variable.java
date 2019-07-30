package net.viperfish.ai.csp;

import java.util.*;

public class Variable<T> {

    private T value;
    private Set<T> domain;

    public Variable(Collection<T> domain) {
        this(null, domain);
    }

    public Variable(T value, Collection<T> domain) {
        this.domain = new HashSet<>(domain);
        this.setValue(value);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        if (value != null && !domain.contains(value)) {
            throw new IllegalArgumentException(String.format("Value %s is not part of the domain", value));
        }
        this.value = value;
    }

    public void eliminate(T value) {
        domain.remove(value);
    }

    public void eliminate(Collection<? extends T> values) {
        domain.removeAll(values);
    }

    public Set<T> getVariation() {
        if (value == null) {
            return new HashSet<>(domain);
        }
        if (domain.contains(value)) {
            return new HashSet<>(Arrays.asList(value));
        }
        return new HashSet<>();
    }

    public Set<T> getRealDomain() {
        return new HashSet<>(domain);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable<?> variable = (Variable<?>) o;
        return Objects.equals(value, variable.value) &&
                Objects.equals(domain, variable.domain);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, domain);
    }
}
