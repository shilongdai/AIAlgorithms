package net.viperfish.ai.csp;

public class RandomRestartMinConflictSearch implements CSPSolver {

    private int maxStep;
    private int maxSidestep;

    public RandomRestartMinConflictSearch(int maxStep, int maxSidestep) {
        this.maxStep = maxStep;
        this.maxSidestep = maxSidestep;
    }

    @Override
    public ConstraintProblem solve(ConstraintProblem problem) {
        ConstraintProblem result = null;
        while (result == null) {
            MinConflictLocalSearch search = new MinConflictLocalSearch(maxStep, maxSidestep);
            result = search.solve(problem);
            problem = LocalCSPSearchUtil.randomVariables(problem);
        }
        return result;
    }
}
