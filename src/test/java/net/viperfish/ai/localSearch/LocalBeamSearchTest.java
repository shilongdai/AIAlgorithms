package net.viperfish.ai.localSearch;

import net.viperfish.ai.NQueenProblem;

public class LocalBeamSearchTest extends LocalSearchTest {
    @Override
    protected LocalSearch<NQueenProblem> getAlg() {
        return new LocalBeamSearch<>();
    }
}
