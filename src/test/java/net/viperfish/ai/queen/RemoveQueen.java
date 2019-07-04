package net.viperfish.ai.queen;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.State;

import java.util.Objects;

public class RemoveQueen implements Action {

    private int row;
    private int col;

    public RemoveQueen(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public NQueenProblem execute(State current) {
        NQueenProblem result = new NQueenProblem((NQueenProblem) current);
        result.removeQueen(row, col);
        return result;
    }

    @Override
    public double cost() {
        return 1;
    }

    @Override
    public Action reverse() {
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
