package net.viperfish.ai.vacuum;

import net.viperfish.ai.search.Precept;
import net.viperfish.ai.search.State;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class DirtyPrecept implements Precept {

    private boolean dirty;

    public DirtyPrecept(boolean dirty) {
        this.dirty = dirty;
    }

    @Override
    public Collection<State> measure() {
        return new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirtyPrecept that = (DirtyPrecept) o;
        return dirty == that.dirty;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dirty);
    }

    @Override
    public String toString() {
        return "DirtyPrecept{" +
                "dirty=" + dirty +
                '}';
    }
}
