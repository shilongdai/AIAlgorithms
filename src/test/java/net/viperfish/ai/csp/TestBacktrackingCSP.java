package net.viperfish.ai.csp;

public class TestBacktrackingCSP extends TestNonLocalCSP {
    @Override
    protected CSPSolver solver() {
        BacktrackingCSPSolver solver = new BacktrackingCSPSolver(new AC3());
        solver.addVarHeuristic(new MinRemainHeuristic());
        solver.addVarHeuristic(new MaxDegreeHeuristic());
        solver.addValHeuristic(new LeastConstrainingHeuristic());
        return solver;
    }
}
