package net.viperfish.ai.queen;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.State;

import java.util.Objects;

public class PlaceQueen implements Action {

    private int row;
    private int col;

    public PlaceQueen(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public NQueenProblem execute(State current) {
        NQueenProblem result = new NQueenProblem((NQueenProblem) current);
        result.placeQueen(row, col);
        return result;
    }

    @Override
    public double cost() {
        return 1;
    }

    @Override
    public Action reverse() {
        return new RemoveQueen(row, col);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceQueen that = (PlaceQueen) o;
        return row == that.row &&
                col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
