package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.NQueenProblem;
import net.viperfish.ai.search.Action;
import org.junit.Assert;
import org.junit.Test;

public abstract class HeuristicProblemSolverTest {

    @Test
    public void testFiveQueen() throws Exception {
        NQueenProblem initial = new NQueenProblem(5);
        HeuristicProblemSolver solver = getSolver();
        for (Action a : solver.solve(initial, new NQueenProblem(0))) {
            initial = (NQueenProblem) a.execute(initial);
        }
        System.out.println(initial.toString());
        Assert.assertTrue(initial.goalReached(initial));
    }

    protected abstract HeuristicProblemSolver getSolver();

}
