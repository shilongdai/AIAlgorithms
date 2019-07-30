package net.viperfish.ai.csp;

public class TestBacktrackingCSP extends TestCSP {
    @Override
    protected CSPSolver solver() {
        return new BacktrackingCSPSolver(new AC3());
    }
}
