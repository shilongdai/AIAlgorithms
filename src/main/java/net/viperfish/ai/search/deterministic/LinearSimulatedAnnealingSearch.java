package net.viperfish.ai.search.deterministic;

public class LinearSimulatedAnnealingSearch extends SimulatedAnnealingSearch {

    private double initialTemp;
    private double rate;

    public LinearSimulatedAnnealingSearch(double initialTemp, double rate) {
        this.initialTemp = initialTemp;
        this.rate = rate;
    }

    @Override
    protected double schedule(int t) {
        return initialTemp - rate * t;
    }
}
