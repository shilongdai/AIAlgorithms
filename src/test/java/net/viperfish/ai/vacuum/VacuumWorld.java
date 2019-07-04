package net.viperfish.ai.vacuum;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.State;
import net.viperfish.ai.search.deterministic.GoalTester;
import net.viperfish.ai.search.partialObserve.BeliefState;

import java.util.*;

public class VacuumWorld implements State, GoalTester {

    protected boolean[] grid;
    protected int x;

    public VacuumWorld(int x) {
        this(x, generateDirt());
    }

    public VacuumWorld(int x, VacuumWorld other) {
        this(x, other.grid);
    }

    public VacuumWorld(int x, boolean[] dusts) {
        this.x = x;
        grid = dusts.clone();
    }

    public static boolean[] generateDirt() {
        return new boolean[]{true, true};
    }

    public boolean clean() {
        for (int i = 0; i < 2; ++i) {
            if (grid[i]) {
                return false;
            }
        }
        return true;
    }

    public void move(int x) {
        if (x > 1 || x < 0) {
            return;
        }
        this.x = x;
    }

    public void suck() {
        grid[x] = true;
    }

    public int getX() {
        return x;
    }

    public boolean currentClean() {
        return !grid[x];
    }

    public void deposit() {
        grid[x] = true;
    }

    @Override
    public Collection<? extends Action> availableActions() {
        List<Action> result = new ArrayList<>();
        if (x == 0) {
            result.add(new MoveVacuum(1));
        } else {
            result.add(new MoveVacuum(0));
        }
        result.add(new SuckDirt());
        return result;
    }

    @Override
    public boolean goalReached(State toTest) {
        BeliefState world = (BeliefState) toTest;
        for (State s : world.possibilities()) {
            VacuumWorld w = (VacuumWorld) s;
            if (!w.clean()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VacuumWorld that = (VacuumWorld) o;
        return x == that.x &&
                Arrays.equals(grid, that.grid);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(x);
        result = 31 * result + Arrays.hashCode(grid);
        return result;
    }

    @Override
    public String toString() {
        return "VacuumWorld {" +
                "grid=" + Arrays.toString(grid) +
                ", x=" + x +
                '}';
    }

}
