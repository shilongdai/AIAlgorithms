package net.viperfish.ai.tictac;

import net.viperfish.ai.search.Action;
import net.viperfish.ai.search.State;
import net.viperfish.ai.search.deterministic.HeuristicGoalTester;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class TicTacGame implements State, HeuristicGoalTester {

    private int[][] grid;
    private boolean max;

    public TicTacGame() {
        this(new int[3][3], true);
    }

    public TicTacGame(TicTacGame game, boolean max) {
        this(game.grid, max);
    }

    private TicTacGame(int[][] grid, boolean max) {
        this.grid = new int[3][];
        for (int i = 0; i < 3; ++i) {
            this.grid[i] = grid[i].clone();
        }
        this.max = max;
    }

    @Override
    public Collection<? extends Action> availableActions() {
        Set<Action> result = new HashSet<>();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (max && grid[i][j] == 0) {
                    result.add(new PlaceMark(i, j, true));
                }
                if (!max && grid[i][j] == 0) {
                    result.add(new PlaceMark(i, j, false));
                }
            }
        }
        return result;
    }

    @Override
    public double heuristic(State state) {
        double minCont = minContinuous((TicTacGame) state);
        double maxCont = maxContinuous((TicTacGame) state);
        if (goalReached(state)) {
            if (minCont == -3) {
                return Double.NEGATIVE_INFINITY;
            } else if (maxCont == 3) {
                return Double.POSITIVE_INFINITY;
            } else {
                return 0;
            }
        }
        return minCont + maxCont;
    }

    @Override
    public boolean goalReached(State toTest) {
        TicTacGame game = (TicTacGame) toTest;
        double minCont = minContinuous((TicTacGame) toTest);
        double maxCont = maxContinuous((TicTacGame) toTest);
        if (minCont == -3 || maxCont == 3) {
            return true;
        }

        for (int[] i : game.grid) {
            for (int j : i) {
                if (j == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public TicTacGame placeX(int x, int y) {
        TicTacGame newGame = new TicTacGame(this, false);
        newGame.grid[x][y] = 1;
        return newGame;
    }

    public TicTacGame placeO(int x, int y) {
        TicTacGame newGame = new TicTacGame(this, true);
        newGame.grid[x][y] = -1;
        return newGame;
    }

    private double minContinuous(TicTacGame game) {
        // check vertical and horizontal
        double[] vertSum = new double[3];
        double rowSumMin = Double.POSITIVE_INFINITY;
        for (int i = 0; i < 3; ++i) {
            double temp = 0;
            for (int j = 0; j < 3; ++j) {
                temp = temp + game.grid[i][j];
                vertSum[j] = vertSum[j] + game.grid[i][j];
            }
            if (temp < rowSumMin) {
                rowSumMin = temp;
            }
        }
        for (double i : vertSum) {
            if (i < rowSumMin) {
                rowSumMin = i;
            }
        }
        return rowSumMin;
    }

    private double maxContinuous(TicTacGame game) {
        // check vertical and horizontal
        double[] vertSum = new double[3];
        double rowSumMax = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < 3; ++i) {
            double temp = 0;
            for (int j = 0; j < 3; ++j) {
                temp = temp + game.grid[i][j];
                vertSum[j] = vertSum[j] + game.grid[i][j];
            }
            if (temp > rowSumMax) {
                rowSumMax = temp;
            }
        }
        for (double i : vertSum) {
            if (i > rowSumMax) {
                rowSumMax = i;
            }
        }
        return rowSumMax;
    }

}
