package net.viperfish.ai.csp;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

    public ConstraintProblem zebraProblem() {
        ConstraintProblem problem = new ConstraintProblem();

        /*
        ------------------------Variables-----------------------------
         */
        // nationality
        problem.addVariable("Irish", new IntegerVariable(null, 0, 5));
        problem.addVariable("Japanese", new IntegerVariable(null, 0, 5));
        problem.addVariable("English", new IntegerVariable(null, 0, 5));
        problem.addVariable("Spaniard", new IntegerVariable(null, 0, 5));
        problem.addVariable("Nigerian", new IntegerVariable(0, 0, 5));

        // house color
        problem.addVariable("Yellow", new IntegerVariable(null, 0, 5));
        problem.addVariable("Red", new IntegerVariable(null, 0, 5));
        problem.addVariable("Green", new IntegerVariable(null, 0, 5));
        problem.addVariable("Ivory", new IntegerVariable(null, 0, 5));
        problem.addVariable("Blue", new IntegerVariable(null, 0, 5));

        // pets
        problem.addVariable("Dog", new IntegerVariable(null, 0, 5));
        problem.addVariable("Snail", new IntegerVariable(null, 0, 5));
        problem.addVariable("Fox", new IntegerVariable(null, 0, 5));
        problem.addVariable("Horse", new IntegerVariable(null, 0, 5));
        problem.addVariable("Zebra", new IntegerVariable(null, 0, 5));

        // hobbies
        problem.addVariable("Go", new IntegerVariable(null, 0, 5));
        problem.addVariable("Cricket", new IntegerVariable(null, 0, 5));
        problem.addVariable("Judo", new IntegerVariable(null, 0, 5));
        problem.addVariable("Poker", new IntegerVariable(null, 0, 5));
        problem.addVariable("Polo", new IntegerVariable(null, 0, 5));

        // drinks
        problem.addVariable("Coffee", new IntegerVariable(null, 0, 5));
        problem.addVariable("Tea", new IntegerVariable(null, 0, 5));
        problem.addVariable("Milk", new IntegerVariable(2, 0, 5));
        problem.addVariable("Juice", new IntegerVariable(null, 0, 5));
        problem.addVariable("Guinness", new IntegerVariable(null, 0, 5));

        /*
        --------------------------------Constraints--------------------------------
         */

        // uniqueness - nationality
        problem.addConstraint(new NotEqualConstraint("Irish", "Japanese"));
        problem.addConstraint(new NotEqualConstraint("Irish", "English"));
        problem.addConstraint(new NotEqualConstraint("Irish", "Spaniard"));
        problem.addConstraint(new NotEqualConstraint("Irish", "Nigerian"));
        problem.addConstraint(new NotEqualConstraint("Japanese", "English"));
        problem.addConstraint(new NotEqualConstraint("Japanese", "Spaniard"));
        problem.addConstraint(new NotEqualConstraint("Japanese", "Nigerian"));
        problem.addConstraint(new NotEqualConstraint("English", "Spaniard"));
        problem.addConstraint(new NotEqualConstraint("English", "Nigerian"));
        problem.addConstraint(new NotEqualConstraint("Spaniard", "Nigerian"));

        // uniqueness - house color
        problem.addConstraint(new NotEqualConstraint("Yellow", "Red"));
        problem.addConstraint(new NotEqualConstraint("Yellow", "Green"));
        problem.addConstraint(new NotEqualConstraint("Yellow", "Ivory"));
        problem.addConstraint(new NotEqualConstraint("Yellow", "Blue"));
        problem.addConstraint(new NotEqualConstraint("Red", "Green"));
        problem.addConstraint(new NotEqualConstraint("Red", "Ivory"));
        problem.addConstraint(new NotEqualConstraint("Red", "Blue"));
        problem.addConstraint(new NotEqualConstraint("Green", "Ivory"));
        problem.addConstraint(new NotEqualConstraint("Green", "Blue"));
        problem.addConstraint(new NotEqualConstraint("Ivory", "Blue"));

        // uniqueness - pet
        problem.addConstraint(new NotEqualConstraint("Dog", "Snail"));
        problem.addConstraint(new NotEqualConstraint("Dog", "Fox"));
        problem.addConstraint(new NotEqualConstraint("Dog", "Horse"));
        problem.addConstraint(new NotEqualConstraint("Dog", "Zebra"));
        problem.addConstraint(new NotEqualConstraint("Snail", "Fox"));
        problem.addConstraint(new NotEqualConstraint("Snail", "Horse"));
        problem.addConstraint(new NotEqualConstraint("Snail", "Zebra"));
        problem.addConstraint(new NotEqualConstraint("Fox", "Zebra"));
        problem.addConstraint(new NotEqualConstraint("Fox", "Horse"));
        problem.addConstraint(new NotEqualConstraint("Zebra", "Horse"));

        // uniqueness - hobbies
        problem.addConstraint(new NotEqualConstraint("Go", "Cricket"));
        problem.addConstraint(new NotEqualConstraint("Go", "Judo"));
        problem.addConstraint(new NotEqualConstraint("Go", "Poker"));
        problem.addConstraint(new NotEqualConstraint("Go", "Polo"));
        problem.addConstraint(new NotEqualConstraint("Cricket", "Judo"));
        problem.addConstraint(new NotEqualConstraint("Cricket", "Poker"));
        problem.addConstraint(new NotEqualConstraint("Cricket", "Polo"));
        problem.addConstraint(new NotEqualConstraint("Judo", "Poker"));
        problem.addConstraint(new NotEqualConstraint("Judo", "Polo"));
        problem.addConstraint(new NotEqualConstraint("Poker", "Polo"));

        // uniqueness - drinks
        problem.addConstraint(new NotEqualConstraint("Coffee", "Tea"));
        problem.addConstraint(new NotEqualConstraint("Coffee", "Milk"));
        problem.addConstraint(new NotEqualConstraint("Coffee", "Juice"));
        problem.addConstraint(new NotEqualConstraint("Coffee", "Guinness"));
        problem.addConstraint(new NotEqualConstraint("Tea", "Milk"));
        problem.addConstraint(new NotEqualConstraint("Tea", "Juice"));
        problem.addConstraint(new NotEqualConstraint("Tea", "Guinness"));
        problem.addConstraint(new NotEqualConstraint("Milk", "Juice"));
        problem.addConstraint(new NotEqualConstraint("Milk", "Guinness"));
        problem.addConstraint(new NotEqualConstraint("Juice", "Guinness"));

        // problem constraints
        problem.addConstraint(new EqualConstraint("English", "Red"));
        problem.addConstraint(new EqualConstraint("Spaniard", "Dog"));
        problem.addConstraint(new EqualConstraint("Green", "Coffee"));
        problem.addConstraint(new EqualConstraint("Irish", "Tea"));
        problem.addConstraint(new GreaterThanConstraint("Green", "Ivory", 1));
        problem.addConstraint(new EqualConstraint("Go", "Snail"));
        problem.addConstraint(new EqualConstraint("Yellow", "Cricket"));
        problem.addConstraint(new DifferentByConstraint("Judo", "Fox", 1));
        problem.addConstraint(new DifferentByConstraint("Cricket", "Horse", 1));
        problem.addConstraint(new EqualConstraint("Poker", "Juice"));
        problem.addConstraint(new EqualConstraint("Japanese", "Polo"));
        problem.addConstraint(new DifferentByConstraint("Nigerian", "Blue", 1));

        return problem;
    }

    @Test
    public void testSolveColorMap() {
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

    @Test
    public void testSolveZebra() {
        ConstraintProblem zebra = zebraProblem();
        CSPSolver solver = solver();
        zebra = solver.solve(zebra);

        Variable<Integer> petZebra = zebra.getVariable("Zebra", Integer.class);
        Variable<Integer> drinkGuinness = zebra.getVariable("Guinness", Integer.class);
        Variable<Integer> japanese = zebra.getVariable("Japanese", Integer.class);
        Variable<Integer> nigerian = zebra.getVariable("Nigerian", Integer.class);
        List<String> sorted = new ArrayList<>(zebra.variables());
        for (String i : sorted) {
            System.out.println(String.format("%s: %d", i, zebra.getVariable(i, Integer.class).getValue()));
        }
        Assert.assertEquals(petZebra.getValue(), japanese.getValue());
        Assert.assertEquals(drinkGuinness.getValue(), nigerian.getValue());
    }

    protected abstract CSPSolver solver();

}
