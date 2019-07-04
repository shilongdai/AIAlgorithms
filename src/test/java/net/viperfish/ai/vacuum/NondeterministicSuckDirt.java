package net.viperfish.ai.vacuum;

import net.viperfish.ai.search.State;
import net.viperfish.ai.search.nondeterministic.NondeterministicAction;
import net.viperfish.ai.search.nondeterministic.NondeterministicState;

import java.util.Arrays;
import java.util.Collection;

public class NondeterministicSuckDirt extends SuckDirt implements NondeterministicAction {

    @Override
    public Collection<NondeterministicState> possibilities(NondeterministicState current) {
        NondeterministicVacuumWorld c = (NondeterministicVacuumWorld) current;
        NondeterministicVacuumWorld pos1 = new NondeterministicVacuumWorld(c.getX(), c);
        NondeterministicVacuumWorld pos2 = new NondeterministicVacuumWorld(c.getX(), c);
        NondeterministicVacuumWorld pos3 = new NondeterministicVacuumWorld(c.getX(), c);

        pos1.reliableSuck(0);
        pos1.reliableSuck(1);

        pos2.reliableSuck(c.getX());

        pos3.deposit();

        if (c.currentClean()) {
            return Arrays.asList(pos2, pos3);
        } else {
            return Arrays.asList(pos2, pos1);
        }
    }

    @Override
    public NondeterministicVacuumWorld execute(State current) {
        VacuumWorld result = super.execute(current);
        return new NondeterministicVacuumWorld(result.getX(), result);
    }

    @Override
    public String toString() {
        return "NondeterministicSuckDirt{}";
    }
}
