package net.viperfish.ai.csp;

public class TestBacktrackingCSP extends TestNonLocalCSP {
    @Override
    protected CSPSolver solver() {
        return new BacktrackingCSPSolver(new AC3());
    }
}
