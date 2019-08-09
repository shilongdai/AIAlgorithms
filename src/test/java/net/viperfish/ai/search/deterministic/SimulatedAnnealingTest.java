package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.queen.NQueenRandomizer;

public class SimulatedAnnealingTest extends LocalSearchTest {
    @Override
    protected LocalSearch getAlg() {
        return new RandomRestartHillClimb(new LinearSimulatedAnnealingSearch(1000, 0.5, 10000), new NQueenRandomizer(8), 100);
    }
}
