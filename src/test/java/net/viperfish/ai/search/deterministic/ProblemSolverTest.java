package net.viperfish.ai.search.deterministic;

import net.viperfish.ai.queen.NQueenProblem;
import net.viperfish.ai.search.Action;
import org.junit.Assert;
import org.junit.Test;

public abstract class ProblemSolverTest {

    @Test
    public void testFiveQueen() throws Exception {
        NQueenProblem initial = new NQueenProblem(5);
        ProblemSolver solver = getSolver();
        for (Action a : solver.solve(initial, new NQueenProblem(4))) {
            initial = (NQueenProblem) a.execute(initial);
        }
        System.out.println(initial.toString());
        Assert.assertTrue(initial.goalReached(initial));
    }

    protected abstract ProblemSolver getSolver();

}
