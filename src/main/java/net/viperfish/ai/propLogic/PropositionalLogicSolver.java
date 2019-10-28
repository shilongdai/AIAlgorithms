package net.viperfish.ai.propLogic;

import java.util.Map;

public interface PropositionalLogicSolver {

    Map<String, Boolean> solve(ConjunctSentence cnf);

}
