package net.viperfish.ai.perceptSearch;

import net.viperfish.ai.classicSearch.Action;
import net.viperfish.ai.classicSearch.State;

import java.util.Map;
import java.util.Objects;

public class ORNode<S extends State> {

    private Map<DeterminingFactor, ANDNode<S>> branches;
    private S state;
    private Action<S> actionTaken;

    public ORNode(Action<S> actionTaken, S state) {
        this.actionTaken = actionTaken;
        this.state = state;
    }

    public Action<S> getActionTaken() {
        return actionTaken;
    }

    public ANDNode<S> branch(DeterminingFactor outcome) {
        return branches.get(outcome);
    }

    public void addBranch(DeterminingFactor outcome, ANDNode<S> branch) {
        branches.put(outcome, branch);
    }

    public S getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ORNode<?> orNode = (ORNode<?>) o;
        return Objects.equals(branches, orNode.branches) &&
                Objects.equals(state, orNode.state) &&
                Objects.equals(actionTaken, orNode.actionTaken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(branches, state, actionTaken);
    }
}
