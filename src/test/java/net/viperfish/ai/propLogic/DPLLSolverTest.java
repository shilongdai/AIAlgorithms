package net.viperfish.ai.propLogic;

public class DPLLSolverTest extends PropositionalLogicSolverTest {
    @Override
    protected PropositionalLogicSolver getSolver() {
        return new DPLLSolver();
    }
}
