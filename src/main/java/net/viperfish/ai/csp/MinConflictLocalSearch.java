package net.viperfish.ai.csp;

import java.util.*;

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
        Set<ConstraintProblem> tabu = Collections.newSetFromMap(new WeakHashMap<>());
        Map<Constraint, Integer> weightMap = new HashMap<>();
        problem = new ConstraintProblem(problem);
        Set<String> conflictSet = LocalCSPSearchUtil.conflictSet(problem);
        if (conflictSet.size() == 0) {
            return problem;
        }
        Random rand = new Random();

        while (currentStep < maxStep) {
            String chosenVarName = LocalCSPSearchUtil.randomConflictVar(conflictSet);
            Variable<Object> var = problem.getVariable(chosenVarName, Object.class);
            Set<Object> domain = var.getRealDomain();
            List<Candidate> sorted = new ArrayList<>();

            for (Object o : domain) {
                var.setValue(o);
                if (tabu.contains(problem)) {
                    continue;
                }

                int conflicts = LocalCSPSearchUtil.weightedConflict(weightMap, problem);
                sorted.add(new Candidate(conflicts, o));
            }
            if (sorted.size() == 0) {
                continue;
            }

            Collections.sort(sorted);

            Candidate chosen = sorted.get(0);
            int sidestep = 0;
            for (; sidestep < sorted.size(); sidestep++) {
                if (sorted.get(sidestep).getConflict() != chosen.getConflict()) {
                    break;
                }
            }
            chosen = sorted.get(rand.nextInt(Math.min(maxSideway, sidestep)));
            var.setValue(chosen.getValue());
            tabu.add(new ConstraintProblem(problem));

            conflictSet = LocalCSPSearchUtil.conflictSet(problem);
            if (conflictSet.size() == 0) {
                return problem;
            }
            currentStep += 1;
        }
        return null;
    }

    private void printCSP(ConstraintProblem csp) {
        System.out.println("\n----------------------Trying-------------------\n");
        for (String i : csp.variables()) {
            Variable<Object> var = csp.getVariable(i, Object.class);
            System.out.println(i + ": " + var.getValue());
        }
        System.out.println("\n------------------------------------------------\n");
    }

    private static class Candidate implements Comparable<Candidate> {
        private int conflict;
        private Object value;

        public Candidate(int conflict, Object value) {
            this.conflict = conflict;
            this.value = value;
        }

        public int getConflict() {
            return conflict;
        }

        public void setConflict(int conflict) {
            this.conflict = conflict;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Candidate candidate = (Candidate) o;
            return conflict == candidate.conflict &&
                    Objects.equals(value, candidate.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(conflict, value);
        }

        @Override
        public int compareTo(Candidate candidate) {
            return Integer.compare(this.conflict, candidate.conflict);
        }

        @Override
        public String toString() {
            return "Candidate{" +
                    "conflict=" + conflict +
                    ", value=" + value +
                    '}';
        }
    }

}
