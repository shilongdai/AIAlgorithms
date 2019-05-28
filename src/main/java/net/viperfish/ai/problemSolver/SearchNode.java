package net.viperfish.ai.problemSolver;

class SearchNode<S extends State> {

    private S parent;
    private Action<S> actionTaken;
    private S current;
    private double cost;

    SearchNode(S parent, Action<S> actionTaken, S current, double cost) {
        this.parent = parent;
        this.actionTaken = actionTaken;
        this.current = current;
        this.cost = cost;
    }

    S getParent() {
        return parent;
    }

    Action<S> getActionTaken() {
        return actionTaken;
    }

    S getCurrent() {
        return current;
    }

    double getCost() {
        return cost;
    }
}
