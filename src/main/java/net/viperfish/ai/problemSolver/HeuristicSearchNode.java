package net.viperfish.ai.problemSolver;

import java.util.Objects;

class HeuristicSearchNode<S extends State> extends SearchNode<S> {

    private double g;
    private double h;

    HeuristicSearchNode(SearchNode<S> parent, Action<S> actionTaken, S current, double cost, double g, double h) {
        super(parent, actionTaken, current, cost);
        this.g = g;
        this.h = h;
    }

    double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        HeuristicSearchNode<?> that = (HeuristicSearchNode<?>) o;
        return Double.compare(that.g, g) == 0 &&
                Double.compare(that.h, h) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), g, h);
    }
}
