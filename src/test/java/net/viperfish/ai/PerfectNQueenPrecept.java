package net.viperfish.ai;

import net.viperfish.ai.perceptSearch.DeterministicPrecept;

public class PerfectNQueenPrecept extends DeterministicPrecept<NQueenProblem> {

    private NQueenProblem state;

    public PerfectNQueenPrecept(NQueenProblem state) {
        this.state = state;
    }


    @Override
    public NQueenProblem getState() {
        return state;
    }
}
