package net.viperfish.ai.localSearch;

import net.viperfish.ai.NQueenProblem;

public class SimulatedAnnealingTest extends LocalSearchTest {
    @Override
    protected LocalSearch<NQueenProblem> getAlg() {
        return new LinearSimulatedAnnealingSearch<>(1000, 1);
    }
}
