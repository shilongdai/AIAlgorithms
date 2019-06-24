package net.viperfish.ai.search.deterministic;

public class SimulatedAnnealingTest extends LocalSearchTest {
    @Override
    protected LocalSearch getAlg() {
        return new LinearSimulatedAnnealingSearch(1000, 1);
    }
}
