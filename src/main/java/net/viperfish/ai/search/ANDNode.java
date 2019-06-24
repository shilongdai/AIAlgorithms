package net.viperfish.ai.search;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ANDNode {

    private Precept outcome;
    private State currentState;
    private List<ORNode> actions;

    public ANDNode(Precept outcome, State currentState) {
        this.outcome = outcome;
        this.currentState = currentState;
        actions = new LinkedList<>();
    }

    public Precept getOutcome() {
        return outcome;
    }

    public State getCurrentState() {
        return currentState;
    }

    public List<ORNode> getActions() {
        return actions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ANDNode andNode = (ANDNode) o;
        return Objects.equals(outcome, andNode.outcome) &&
                Objects.equals(currentState, andNode.currentState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(outcome, currentState);
    }
}
