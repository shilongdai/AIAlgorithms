package net.viperfish.ai.csp;

import org.junit.Assert;
import org.junit.Test;

public abstract class TestCSP {

    public ConstraintProblem mapColoring() {
        ConstraintProblem csp = new ConstraintProblem();
        csp.addVariable("WA", new MapColor(null));
        csp.addVariable("NT", new MapColor(null));
        csp.addVariable("SA", new MapColor(null));
        csp.addVariable("QLD", new MapColor(null));
        csp.addVariable("NSW", new MapColor(null));
        csp.addVariable("VIC", new MapColor(null));
        csp.addVariable("LS", new MapColor(null));

        // WA Constraints
        csp.addConstraint(new NotEqualConstraint("WA", "NT"));
        csp.addConstraint(new NotEqualConstraint("WA", "SA"));

        // NT Constraints
        csp.addConstraint(new NotEqualConstraint("NT", "SA"));
        csp.addConstraint(new NotEqualConstraint("NT", "QLD"));

        // SA Constraints
        csp.addConstraint(new NotEqualConstraint("SA", "QLD"));
        csp.addConstraint(new NotEqualConstraint("SA", "NSW"));
        csp.addConstraint(new NotEqualConstraint("SA", "VIC"));

        // QLD Constraints
        csp.addConstraint(new NotEqualConstraint("QLD", "NSW"));

        // NSW Constraints
        csp.addConstraint(new NotEqualConstraint("NSW", "VIC"));

        // VIC Constraints
        csp.addConstraint(new NotEqualConstraint("VIC", "LS"));

        return csp;
    }

    @Test
    public void testSolve() {
        ConstraintProblem mapColoring = mapColoring();
        CSPSolver solver = solver();
        mapColoring = solver.solve(mapColoring);

        Variable<Color> wa = mapColoring.getVariable("WA", Color.class);
        Variable<Color> nt = mapColoring.getVariable("NT", Color.class);
        Variable<Color> sa = mapColoring.getVariable("SA", Color.class);
        Variable<Color> qld = mapColoring.getVariable("QLD", Color.class);
        Variable<Color> nsw = mapColoring.getVariable("NSW", Color.class);
        Variable<Color> vic = mapColoring.getVariable("VIC", Color.class);
        Variable<Color> ls = mapColoring.getVariable("LS", Color.class);

        Assert.assertNotEquals(wa.getValue(), nt.getValue());
        Assert.assertNotEquals(wa.getValue(), sa.getValue());
        Assert.assertNotEquals(nt.getValue(), sa.getValue());
        Assert.assertNotEquals(nt.getValue(), qld.getValue());
        Assert.assertNotEquals(sa.getValue(), qld.getValue());
        Assert.assertNotEquals(sa.getValue(), nsw.getValue());
        Assert.assertNotEquals(sa.getValue(), vic.getValue());
        Assert.assertNotEquals(qld.getValue(), nsw.getValue());
        Assert.assertNotEquals(nsw.getValue(), vic.getValue());
        Assert.assertNotEquals(vic.getValue(), ls.getValue());

        System.out.println("West Australia: " + wa.getValue());
        System.out.println("Northern Territory: " + nt.getValue());
        System.out.println("South Australia: " + sa.getValue());
        System.out.println("Queensland:" + qld.getValue());
        System.out.println("New South Wale:" + nsw.getValue());
        System.out.println("Victoria: " + vic.getValue());
        System.out.println("Launcestone:" + ls.getValue());
    }

    protected abstract CSPSolver solver();

}
