package net.viperfish.ai.tictac;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.State;

import java.util.Objects;

public class PlaceMark implements Action {

    private int x;
    private int y;
    private boolean isX;

    public PlaceMark(int x, int y, boolean isX) {
        this.x = x;
        this.y = y;
        this.isX = isX;
    }

    @Override
    public State execute(State current) {
        if (!(current instanceof TicTacGame)) {
            throw new IllegalArgumentException(current.toString());
        }
        TicTacGame game = (TicTacGame) current;
        if (isX) {
            return game.placeX(x, y);
        } else {
            return game.placeO(x, y);
        }
    }

    @Override
    public double cost() {
        return 0;
    }

    @Override
    public Action reverse() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceMark placeMark = (PlaceMark) o;
        return x == placeMark.x &&
                y == placeMark.y &&
                isX == placeMark.isX;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, isX);
    }
}
