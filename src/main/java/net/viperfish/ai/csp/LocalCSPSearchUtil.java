package net.viperfish.ai.csp;

import java.util.*;

public final class LocalCSPSearchUtil {

    private LocalCSPSearchUtil() {

    }

    public static int countConflict(ConstraintProblem csp, String var) {
        int result = 0;
        for (Constraint c : csp.constraints(var)) {
            if (!c.validate(csp)) {
                result += 1;
            }
        }
        return result;
    }

    public static Set<String> conflictSet(ConstraintProblem csp) {
        Set<String> result = new HashSet<>();
        for (String i : csp.variables()) {
            if (countConflict(csp, i) != 0) {
                result.add(i);
            }
        }
        return result;
    }

    public static String randomConflictVar(Set<String> conflictVar) {
        List<String> conflicts = new ArrayList<>(conflictVar);
        Random rand = new Random();
        return conflicts.get(rand.nextInt(conflicts.size()));
    }

    public static int weightedConflict(Map<Constraint, Integer> weights, ConstraintProblem csp) {
        int result = 0;
        for (String i : csp.variables()) {
            for (Constraint c : csp.constraints(i)) {
                if (!c.validate(csp)) {
                    if (!weights.containsKey(c)) {
                        weights.put(c, 1);
                    }
                    int weight = weights.get(c);
                    result += weight;
                    weights.put(c, weight + 1);
                }
            }
        }
        return result;
    }

    public static ConstraintProblem randomVariables(ConstraintProblem csp) {
        Random rand = new Random();
        csp = new ConstraintProblem(csp);
        for (String i : csp.variables()) {
            Variable<Object> var = csp.getVariable(i, Object.class);
            List<Object> domain = new ArrayList<>(var.getRealDomain());
            Object val = domain.get(rand.nextInt(domain.size()));
            var.setValue(val);
        }
        return csp;
    }

}
