package net.viperfish.ai.csp;

public class TestMinConflict extends TestLocalCSP {
    @Override
    protected CSPSolver solver() {
        return new RandomRestartMinConflictSearch(200, 3);
    }
}
