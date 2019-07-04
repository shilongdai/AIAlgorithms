package net.viperfish.ai.vacuum;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.State;

import java.util.Objects;

public class MoveVacuum implements Action {

    protected int newX;

    public MoveVacuum(int newX) {
        this.newX = newX;
    }

    @Override
    public VacuumWorld execute(State current) {
        VacuumWorld world = (VacuumWorld) current;
        VacuumWorld newWorld = new VacuumWorld(world.getX(), world);
        newWorld.move(newX);
        return newWorld;
    }

    @Override
    public double cost() {
        return 1;
    }

    @Override
    public Action reverse() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoveVacuum that = (MoveVacuum) o;
        return newX == that.newX;
    }

    @Override
    public int hashCode() {
        return Objects.hash(newX);
    }

    @Override
    public String toString() {
        return "MoveVacuum {" +
                "newX=" + newX +
                '}';
    }

}
