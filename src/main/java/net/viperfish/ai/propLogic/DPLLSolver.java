package net.viperfish.ai.propLogic;

import java.util.*;

public class DPLLSolver implements PropositionalLogicSolver {


    @Override
    public Map<String, Boolean> solve(ConjunctSentence cnf) {
        Set<Map<String, CNFSymbol>> clauses = CNFUtils.asCNFSymbols(cnf);
        Map<String, Set<Map<String, CNFSymbol>>> clauseMapping = literalClauseMapping(clauses);
        Set<CNFSymbol> pureSymbols = pureSymbols(clauseMapping);
        Set<String> unassignedSet = new HashSet<>(clauseMapping.keySet());

        Stack<String> assignments = new Stack<>();
        Map<String, List<Boolean>> untried = new HashMap<>();
        Map<String, Boolean> currentAssignment = new HashMap<>();

        for (CNFSymbol c : pureSymbols) {
            currentAssignment.put(c.getLiteral(), !c.isNegate());
            unassignedSet.remove(c.getLiteral());
        }
        List<String> unassigned = new ArrayList<>(unassignedSet);
        for (String s : unassigned) {
            untried.put(s, new ArrayList<>(Arrays.asList(true, false)));
        }

        while (!unassigned.isEmpty()) {
            String next = unassigned.get(0);
            List<Boolean> untriedList = untried.get(next);
            if (untriedList.isEmpty()) {
                if (assignments.size() == 0) {
                    break;
                }
                untriedList.add(true);
                untriedList.add(false);
                unassigned.add(0, assignments.pop());
                continue;
            }
            boolean nextVal = untriedList.remove(0);
            currentAssignment.put(next, nextVal);
            if (!sentenceFalse(clauses, currentAssignment)) {
                assignments.push(next);
                unassigned.remove(0);
            } else {
                currentAssignment.remove(next);
            }
        }

        if (currentAssignment.size() == clauseMapping.size()) {
            return currentAssignment;
        }
        return null;
    }


    private Map<String, Set<Map<String, CNFSymbol>>> literalClauseMapping(Set<Map<String, CNFSymbol>> clauses) {
        Map<String, Set<Map<String, CNFSymbol>>> result = new HashMap<>();

        for (Map<String, CNFSymbol> clause : clauses) {
            for (CNFSymbol symbol : clause.values()) {
                if (!result.containsKey(symbol.getLiteral())) {
                    result.put(symbol.getLiteral(), new HashSet<>());
                }

                result.get(symbol.getLiteral()).add(clause);
            }
        }

        return result;
    }

    private Set<CNFSymbol> pureSymbols(Map<String, Set<Map<String, CNFSymbol>>> clauseMap) {
        Set<CNFSymbol> result = new HashSet<>();

        for (Map.Entry<String, Set<Map<String, CNFSymbol>>> e : clauseMap.entrySet()) {
            int negateCount = 0;
            int nonNegateCount = 0;
            boolean isPure = true;
            for (Map<String, CNFSymbol> clause : e.getValue()) {
                if (clause.get(e.getKey()).isNegate()) {
                    negateCount += 1;
                } else {
                    nonNegateCount += 1;
                }
                if (negateCount * nonNegateCount != 0) {
                    isPure = false;
                    break;
                }
            }

            if (isPure) {
                result.add(e.getValue().iterator().next().get(e.getKey()));
            }
        }
        return result;
    }

    private boolean sentenceFalse(Set<Map<String, CNFSymbol>> clauses, Map<String, Boolean> assignment) {
        for (Map<String, CNFSymbol> clause : clauses) {
            boolean clauseTruth = false;
            for (Map.Entry<String, CNFSymbol> symbolEntry : clause.entrySet()) {
                if (!assignment.containsKey(symbolEntry.getKey())) {
                    clauseTruth = true;
                    break;
                }
                boolean assigned = assignment.get(symbolEntry.getKey());
                if (symbolEntry.getValue().isNegate()) {
                    assigned = !assigned;
                }

                if (assigned) {
                    clauseTruth = true;
                    break;
                }
            }
            if (!clauseTruth) {
                return true;
            }
        }
        return false;
    }

}
