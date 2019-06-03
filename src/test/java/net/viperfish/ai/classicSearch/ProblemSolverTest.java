package net.viperfish.ai.classicSearch;

import net.viperfish.ai.NQueenProblem;
import org.junit.Assert;
import org.junit.Test;

public abstract class ProblemSolverTest {

    @Test
    public void testFiveQueen() throws Exception {
        NQueenProblem initial = new NQueenProblem(5);
        ProblemSolver<NQueenProblem> solver = getSolver();
        for (Action<NQueenProblem> a : solver.solve(initial, new NQueenProblem(4))) {
            initial = a.execute(initial);
        }
        System.out.println(initial.toString());
        Assert.assertTrue(initial.goalReached(initial));
    }

    protected abstract ProblemSolver<NQueenProblem> getSolver();

}
