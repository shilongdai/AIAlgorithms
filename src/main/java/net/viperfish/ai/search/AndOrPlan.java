package net.viperfish.ai.search;

public class AndOrPlan implements Plan {

    private ORNode root;
    private ORNode current;
    private boolean endReached;

    public AndOrPlan(ORNode root) {
        this.root = root;
        endReached = false;
    }

    @Override
    public Action next(Precept precept) {
        if (current == null) {
            current = root;
            return root.getActionTaken();
        }
        ANDNode possibility = current.branch(precept);
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
