package net.viperfish.ai.csp;

public class TestMinConflict extends TestLocalCSP {
    @Override
    protected CSPSolver solver() {
        return new MinConflictLocalSearch(Integer.MAX_VALUE, 2);
    }
}
