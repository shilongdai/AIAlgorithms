package net.viperfish.ai.csp;

import java.util.Random;
import java.util.Set;

public class MinConflictLocalSearch implements CSPSolver {

    private int maxStep;
    private int maxSideway;

    public MinConflictLocalSearch(int maxStep, int maxSideway) {
        this.maxStep = maxStep;
        this.maxSideway = maxSideway;
    }

    @Override
    public ConstraintProblem solve(ConstraintProblem problem) {
        int currentStep = 0;
        problem = new ConstraintProblem(problem);
        Set<String> conflictSet = LocalCSPSearchUtil.conflictSet(problem);
        if (conflictSet.size() == 0) {
            return problem;
        }
        Random rand = new Random();

        while (currentStep < maxStep) {
            String chosenVarName = LocalCSPSearchUtil.randomConflictVar(conflictSet);
            Variable<Object> var = problem.getVariable(chosenVarName, Object.class);
            int best = Integer.MAX_VALUE;
            Object bestVal = null;
            Set<Object> domain = var.getRealDomain();
            int maxSideway = rand.nextInt(this.maxSideway);
            int currentSideway = 0;

            for (Object o : domain) {
                var.setValue(o);
                int conflicts = LocalCSPSearchUtil.countConflict(problem, chosenVarName);
                if (conflicts < best) {
                    best = conflicts;
                    bestVal = o;
                    currentSideway = 0;
                }
                if (conflicts == best) {
                    if (currentSideway++ < maxSideway) {
                        bestVal = o;
                    }
                }
            }

            var.setValue(bestVal);
            conflictSet = LocalCSPSearchUtil.conflictSet(problem);
            if (conflictSet.size() == 0) {
                return problem;
            }
            currentStep += 1;
        }
        return null;
    }

}
