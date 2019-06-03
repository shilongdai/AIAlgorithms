package net.viperfish.ai;

import net.viperfish.ai.classicSearch.Action;

import java.util.Objects;

public class MoveQueen implements Action<NQueenProblem> {

    private int origR;
    private int origC;
    private int newR;
    private int newC;

    public MoveQueen(int origR, int origC, int newR, int newC) {
        this.origR = origR;
        this.origC = origC;
        this.newR = newR;
        this.newC = newC;
    }

    @Override
    public NQueenProblem predict(NQueenProblem current) {
        NQueenProblem result = new NQueenProblem(current);
        result.moveQueen(origR, origC, newR, newC);
        return result;
    }

    @Override
    public NQueenProblem execute(NQueenProblem current) throws Exception {
        return predict(current);
    }

    @Override
    public double cost() {
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoveQueen moveQueen = (MoveQueen) o;
        return origR == moveQueen.origR &&
                origC == moveQueen.origC &&
                newR == moveQueen.newR &&
                newC == moveQueen.newC;
    }

    @Override
    public int hashCode() {
        return Objects.hash(origR, origC, newR, newC);
    }
}
