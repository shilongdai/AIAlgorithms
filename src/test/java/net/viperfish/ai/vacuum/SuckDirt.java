package net.viperfish.ai.vacuum;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.State;

public class SuckDirt implements Action {

    @Override
    public VacuumWorld execute(State current) {
        VacuumWorld vacuumWorld = (VacuumWorld) current;
        VacuumWorld newWorld = new NondeterministicVacuumWorld(vacuumWorld.getX(), vacuumWorld);
        newWorld.suck();
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
    public String toString() {
        return "SuckDirt{}";
    }
}
