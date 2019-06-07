package net.viperfish.ai;

import net.viperfish.ai.classicSearch.Action;

import java.util.Objects;

public class RemoveQueen implements Action<NQueenProblem> {

    private int row;
    private int col;

    public RemoveQueen(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public NQueenProblem predict(NQueenProblem current) {
        NQueenProblem result = new NQueenProblem(current);
        result.removeQueen(row, col);
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
    public Action<NQueenProblem> reverse() {
        return new PlaceQueen(row, col);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RemoveQueen that = (RemoveQueen) o;
        return row == that.row &&
                col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
