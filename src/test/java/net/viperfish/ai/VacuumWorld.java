package net.viperfish.ai;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.State;
import net.viperfish.ai.search.deterministic.GoalTester;

import java.util.Collection;
import java.util.Random;

public class VacuumWorld implements State, GoalTester {

    private boolean[][] grid;
    private int x;
    private int y;

    public VacuumWorld(int x, int y) {
        grid = new boolean[4][4];
        this.x = x;
        this.y = y;
    }

    public VacuumWorld(int x, int y, boolean[][] dusts) {
        this.x = x;
        this.y = y;
        grid = new boolean[4][];
        for (int i = 0; i < 4; ++i) {
            grid[i] = dusts[i].clone();
        }
    }

    @Override
    public Collection<? extends Action> availableActions() {
        return null;
    }

    @Override
    public boolean goalReached(State toTest) {
        return false;
    }

    private boolean[][] generateDirt() {
        Random rand = new Random();
        boolean[][] result = new boolean[4][4];
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                result[i][j] = rand.nextBoolean();
            }
        }
        return result;
    }
}
