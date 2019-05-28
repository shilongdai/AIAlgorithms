package net.viperfish.ai.problemSolver;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Simplified5Queen implements HeuristicGoalTester<Simplified5Queen>, State {

    private boolean[][] board;
    private int queenCount;

    public Simplified5Queen() {
        board = new boolean[5][5];
        for(int i = 0; i < board.length; ++i) {
            for(int j = 0; j < board[i].length; ++j) {
                board[i][j] = false;
            }
        }
        queenCount = 0;
    }

    public Simplified5Queen(Simplified5Queen previous, int x, int y) {
        this();
        for(int i = 0; i < board.length; ++i) {
            for(int j = 0; j < board[i].length; ++j) {
                board[i][j] = previous.board[i][j];
            }
        }
        this.queenCount = previous.queenCount;
        if(!board[x][y]) {
            board[x][y] = true;
            queenCount += 1;
        }
    }

    @Override
    public boolean goalReached(Simplified5Queen toTest) {
        return heuristic(toTest) == 0;
    }

    @Override
    public Iterable<PlaceQueen> availableActions() {
        List<PlaceQueen> result = new LinkedList<>();
        for(int i = 0; i < board.length; ++i) {
            boolean queen = false;
            List<PlaceQueen> buffer = new LinkedList<>();
            for(int j = 0; j < board[i].length; ++j) {
                if(board[i][j] && queen) {
                    buffer.clear();
                    break;
                }
                if(board[i][j]) {
                    queen = true;
                }
                buffer.add(new PlaceQueen(i, j));
            }
            result.addAll(buffer);
        }
        return result;
    }

    @Override
    public double heuristic(Simplified5Queen toTest) {
        boolean[][] board = toTest.board;
        boolean satisfied = true;
        for(int row = 0; row < board.length; ++row) {
            boolean queen = false;
            for(int col = 0; col < board[row].length; ++col) {
                if(board[row][col] && queen) {
                    satisfied = false;
                    break;
                }
                if(board[row][col]) {
                    queen = true;
                }
            }
            if(!satisfied) {
                break;
            }
        }
        int colCount = 5;
        if(satisfied) {
            for (int col = 0; col < board[0].length; ++col) {
                boolean queen = false;
                for (int row = 0; row < board.length; ++row) {
                    if (board[row][col] && queen) {
                        satisfied = false;
                        break;
                    }
                    if (board[row][col]) {
                        queen = true;
                        colCount -= 1;
                    }
                }
                queen = false;
            }
        }
        if(satisfied && toTest.queenCount == 5) {
            return 0;
        }
        return colCount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < board.length; ++i) {
            for(int j = 0; j < board[i].length; ++j) {
                if(!board[i][j]) {
                    sb.append(" X ");
                } else {
                    sb.append(" Q ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Simplified5Queen that = (Simplified5Queen) o;
        if (queenCount != that.queenCount) {
            return false;
        }
        for(int i = 0; i < board.length; ++i) {
            if(!Arrays.equals(board[i], that.board[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(queenCount);
        for(int i = 0; i < board.length; ++i) {
            result = 31 * result + Arrays.hashCode(board[i]);
        }
        return result;
    }
}
