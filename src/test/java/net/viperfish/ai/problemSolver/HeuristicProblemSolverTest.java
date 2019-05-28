package net.viperfish.ai.problemSolver;

import org.junit.Assert;
import org.junit.Test;

public abstract class HeuristicProblemSolverTest {

    @Test
    public void testFiveQueen() throws Exception {
        Simplified5Queen initial = new Simplified5Queen();
        HeuristicProblemSolver<Simplified5Queen> solver = getSolver();
        for(Action<Simplified5Queen> a : solver.solve(initial, new Simplified5Queen())) {
            initial = a.execute(initial);
        }
        System.out.println(initial.toString());
        Assert.assertTrue(initial.goalReached(initial));
    }

    protected abstract HeuristicProblemSolver<Simplified5Queen> getSolver();

}
