package net.viperfish.ai.vacuum;

import net.viperfish.ai.search.deterministic.GoalTester;
import net.viperfish.ai.search.nondeterministic.NondeterministicAction;
import net.viperfish.ai.search.nondeterministic.NondeterministicState;

import java.util.*;

public class NondeterministicVacuumWorld extends VacuumWorld implements NondeterministicState, GoalTester {

    public NondeterministicVacuumWorld(int x) {
        super(x, generateDirt());
    }

    public NondeterministicVacuumWorld(int x, VacuumWorld other) {
        super(x, other.grid);
    }

    public void suck() {
        Random rand = new Random();
        double roll = rand.nextDouble();
        if (roll < 0.2) {
            grid[0] = false;
            grid[1] = false;
        } else {
            if (grid[x]) {
                grid[x] = false;
            } else if (roll > 0.8) {
                grid[x] = true;
            }
        }

    }

    public void reliableSuck(int pos) {
        grid[pos] = false;
    }

    @Override
    public Collection<? extends NondeterministicAction> availableActions() {
        List<NondeterministicAction> result = new ArrayList<>();
        if (x == 0) {
            result.add(new NondeterministicMoveVacuum(1));
        } else {
            result.add(new NondeterministicMoveVacuum(0));
        }
        result.add(new NondeterministicSuckDirt());
        return result;
    }

    @Override
    public String toString() {
        return "NondeterministicVacuumWorld{" +
                "grid=" + Arrays.toString(grid) +
                ", x=" + x +
                '}';
    }
}
