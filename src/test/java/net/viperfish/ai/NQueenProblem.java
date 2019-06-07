package net.viperfish.ai;

import net.viperfish.ai.classicSearch.Action;
import net.viperfish.ai.classicSearch.HeuristicGoalTester;
import net.viperfish.ai.classicSearch.State;
import net.viperfish.ai.localSearch.GeneticState;
import net.viperfish.ai.localSearch.ObjectiveFunction;

import java.util.*;

public class NQueenProblem implements GeneticState, HeuristicGoalTester<NQueenProblem>, ObjectiveFunction<NQueenProblem> {

    private int n;
    private int[][] board;
    private Map<Integer, Integer> queenTracker;

    public NQueenProblem(int n) {
        this.n = n;
        board = new int[n][n];
        for (int[] i : board) {
            Arrays.fill(i, 0);
        }
        queenTracker = new HashMap<>();
    }

    public NQueenProblem(NQueenProblem nQueenProblem) {
        this.n = nQueenProblem.n;
        board = new int[n][n];
        this.queenTracker = new HashMap<>(nQueenProblem.queenTracker);
        for (int i = 0; i < n; ++i) {
            this.board[i] = nQueenProblem.board[i].clone();
        }
    }

    public void placeQueen(int row, int col) {
        board[row][col] = 1;
        queenTracker.put(row, col);
    }

    public void moveQueen(int r1, int c1, int r2, int c2) {
        board[r2][c2] = board[r1][c1];
        board[r1][c1] = 0;
        queenTracker.remove(r1);
        queenTracker.put(r2, c2);
    }

    public void removeQueen(int row, int col) {
        board[row][col] = 0;
        queenTracker.remove(row);
    }

    public int getN() {
        return n;
    }

    @Override
    public double heuristic(NQueenProblem state) {
        double h = 0;
        for (Map.Entry<Integer, Integer> e : state.queenTracker.entrySet()) {
            h += conflict(state.board, e.getKey(), e.getValue(), state.n);
        }
        h = h / 2;
        return h;
    }

    @Override
    public double evaluate(NQueenProblem state) {
        return 1 / heuristic(state);
    }

    @Override
    public boolean goalReached(NQueenProblem toTest) {
        if (Math.abs(this.heuristic(toTest) - 0) > Double.MIN_NORMAL) {
            return false;
        }
        return toTest.queenTracker.size() == toTest.n;
    }

    @Override
    public Collection<? extends Action<? extends State>> availableActions() {
        Set<Action<NQueenProblem>> actionSet = new HashSet<>();
        if (queenTracker.size() < n) {
            Set<Integer> availableRow = new HashSet<>();
            for (int i = 0; i < n; ++i) {
                availableRow.add(i);
            }
            for (Map.Entry<Integer, Integer> e : queenTracker.entrySet()) {
                availableRow.remove(e.getKey());
            }
            for (Integer row : availableRow) {
                for (int i = 0; i < n; ++i) {
                    actionSet.add(new PlaceQueen(row, i));
                }
            }
        }
        for (Map.Entry<Integer, Integer> e : queenTracker.entrySet()) {
            for (int i = 0; i < n; ++i) {
                if (i != e.getValue()) {
                    actionSet.add(new MoveQueen(e.getKey(), e.getValue(), e.getKey(), i));
                }
            }
        }
        return actionSet;
    }

    @Override
    public GeneticState reproduce(GeneticState other) {
        NQueenProblem otherProblem = (NQueenProblem) other;
        Random rand = new Random();
        int cutoff = rand.nextInt(n);
        NQueenProblem result = new NQueenProblem(n);
        result.queenTracker.clear();
        for (int i = 0; i < cutoff; ++i) {
            if (queenTracker.containsKey(i)) {
                result.placeQueen(i, queenTracker.get(i));
            }
        }
        for (int i = cutoff; i < n; ++i) {
            if (otherProblem.queenTracker.containsKey(i)) {
                result.placeQueen(i, otherProblem.queenTracker.get(i));
            }
        }
        return result;
    }

    @Override
    public void mutate() {
        Random rand = new Random();
        int newCol = rand.nextInt(n);
        int row = rand.nextInt(n);
        moveQueen(row, queenTracker.get(row), row, newCol);
    }

    private int conflict(int[][] board, int row, int col, int n) {
        int i, j;
        int conflict = 0;

        /* Check this row on left and right side */
        for (i = 0; i < col; i++) {
            if (board[row][i] == 1)
                conflict += 1;
        }

        for (i = col + 1; i < n; ++i) {
            if (board[row][i] == 1)
                conflict += 1;
        }

        /*
            checks col
         */
        for (i = 0; i < row; i++) {
            if (board[i][col] == 1)
                conflict += 1;
        }

        for (i = row + 1; i < n; ++i) {
            if (board[i][col] == 1)
                conflict += 1;
        }

        /* Check upper diagonal on left and right side */
        for (i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1)
                conflict += 1;
        }

        for (i = row + 1, j = col + 1; i < n && j < n; i++, j++) {
            if (board[i][j] == 1)
                conflict += 1;
        }

        /* Check lower diagonal on left and right side */
        for (i = row + 1, j = col - 1; j >= 0 && i < n; i++, j--) {
            if (board[i][j] == 1)
                conflict += 1;
        }

        for (i = row - 1, j = col + 1; j < n && i >= 0; i--, j++) {
            if (board[i][j] == 1)
                conflict += 1;
        }

        return conflict;
    }

    private boolean boardEqual(NQueenProblem nQueenProblem) {
        if (nQueenProblem.n != n) {
            return false;
        }
        for (int i = 0; i < n; ++i) {
            if (!Arrays.equals(nQueenProblem.board[i], board[i])) {
                return false;
            }
        }
        return true;
    }

    private int boardHash() {
        int result = 0;
        for (int i = 0; i < board.length; ++i) {
            result = 31 * result + Arrays.hashCode(board[i]);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NQueenProblem that = (NQueenProblem) o;
        return n == that.n &&
                boardEqual(that) &&
                queenTracker.equals(that.queenTracker);
    }

    @Override
    public int hashCode() {
        int result = queenTracker.hashCode();
        result = 31 * result + Integer.hashCode(n);
        result = 31 * result + boardHash();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                sb.append(board[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
