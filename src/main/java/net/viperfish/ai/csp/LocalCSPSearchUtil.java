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

}
