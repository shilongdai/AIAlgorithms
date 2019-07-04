package net.viperfish.ai.queen;

import net.viperfish.ai.search.PerfectPrecept;

public class PerfectNQueenPrecept extends PerfectPrecept {

    private NQueenProblem state;

    public PerfectNQueenPrecept(NQueenProblem state) {
        this.state = state;
    }


    @Override
    public NQueenProblem getState() {
        return state;
    }
}
