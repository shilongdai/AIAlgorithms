package net.viperfish.ai;

import net.viperfish.ai.search.Precept;

public class PerfectNQueenPrecept implements Precept {

    private NQueenProblem state;

    public PerfectNQueenPrecept(NQueenProblem state) {
        this.state = state;
    }


    @Override
    public NQueenProblem getState() {
        return state;
    }
}
