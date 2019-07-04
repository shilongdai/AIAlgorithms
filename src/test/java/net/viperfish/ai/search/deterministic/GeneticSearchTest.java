package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.queen.NQueenProblem;

public class GeneticSearchTest extends LocalSearchTest {
    @Override
    protected LocalSearch getAlg() {
        return new GeneticSearch(0.05, new NQueenProblem(1));
    }
}
