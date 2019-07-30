package net.viperfish.ai.csp;

import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

class BackTrackStatus {

    private ConstraintProblem csp;
    private SortedSet<Integer> conflictSet;

    public BackTrackStatus(ConstraintProblem csp, SortedSet<Integer> backtrackDepth) {
        this.csp = csp;
        this.conflictSet = new TreeSet<>(backtrackDepth);
    }

    public ConstraintProblem getCsp() {
        return csp;
    }

    public SortedSet<Integer> getConflictSet() {
        return new TreeSet<>(this.conflictSet);
    }

    public boolean failed() {
        return csp == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BackTrackStatus that = (BackTrackStatus) o;
        return Objects.equals(csp, that.csp) &&
                Objects.equals(conflictSet, that.conflictSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(csp, conflictSet);
    }
}
