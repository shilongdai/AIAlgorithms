package net.viperfish.ai.localSearch;

import net.viperfish.ai.NQueenProblem;

public class GeneticSearchTest extends LocalSearchTest {
    @Override
    protected LocalSearch<NQueenProblem> getAlg() {
        return new GeneticSearch<>(0.05);
    }
}
