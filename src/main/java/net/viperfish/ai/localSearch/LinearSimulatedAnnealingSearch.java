package net.viperfish.ai.localSearch;

import net.viperfish.ai.classicSearch.State;

public class LinearSimulatedAnnealingSearch<S extends State> extends SimulatedAnnealingSearch<S> {

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
