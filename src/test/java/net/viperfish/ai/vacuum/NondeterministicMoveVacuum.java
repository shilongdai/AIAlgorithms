package net.viperfish.ai.vacuum;

import net.viperfish.ai.search.State;
import net.viperfish.ai.search.nondeterministic.NondeterministicAction;
import net.viperfish.ai.search.nondeterministic.NondeterministicState;

import java.util.Arrays;
import java.util.Collection;

public class NondeterministicMoveVacuum extends MoveVacuum implements NondeterministicAction {

    public NondeterministicMoveVacuum(int newX) {
        super(newX);
    }

    @Override
    public Collection<NondeterministicState> possibilities(NondeterministicState current) {
        NondeterministicVacuumWorld world1 = new NondeterministicVacuumWorld(newX, (NondeterministicVacuumWorld) current);
        return Arrays.asList(world1);
    }

    @Override
    public NondeterministicVacuumWorld execute(State current) {
        VacuumWorld result = super.execute(current);
        return new NondeterministicVacuumWorld(result.getX(), result);
    }

    @Override
    public String toString() {
        return "NondeterministicMoveVacuum{" +
                "newX=" + newX +
                '}';
    }
}
