package net.viperfish.ai.perceptSearch;

import net.viperfish.ai.classicSearch.State;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ANDNode<S extends State> {

    private DeterminingFactor outcome;
    private S currentState;
    private List<ORNode<S>> actions;

    public ANDNode(DeterminingFactor outcome, S currentState) {
        this.outcome = outcome;
        this.currentState = currentState;
        actions = new LinkedList<>();
    }

    public DeterminingFactor getOutcome() {
        return outcome;
    }

    public S getCurrentState() {
        return currentState;
    }

    public List<ORNode<S>> getActions() {
        return actions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ANDNode<?> andNode = (ANDNode<?>) o;
        return Objects.equals(outcome, andNode.outcome) &&
                Objects.equals(currentState, andNode.currentState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(outcome, currentState);
    }
}
