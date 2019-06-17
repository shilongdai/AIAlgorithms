package net.viperfish.ai.perceptSearch;

import net.viperfish.ai.classicSearch.Action;
import net.viperfish.ai.classicSearch.State;

public class AndOrPlan<S extends State> implements Plan<S> {

    private ORNode<S> root;
    private ORNode<S> current;
    private boolean endReached;

    public AndOrPlan(ORNode<S> root) {
        this.root = root;
        endReached = false;
    }

    @Override
    public Action<S> next(Precept<S> precept) {
        if (current == null) {
            current = root;
            return root.getActionTaken();
        }
        ANDNode<S> possibility = current.branch(precept);
        if (possibility == null) {
            endReached = true;
            return null;
        }
        return current.getActionTaken();
    }

    @Override
    public boolean finished() {
        return endReached;
    }
}
