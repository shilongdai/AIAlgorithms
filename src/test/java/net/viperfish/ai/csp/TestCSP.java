package net.viperfish.ai.csp;

import org.junit.Assert;

public class TestCSP {
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


    protected void checkSolvedMap(ConstraintProblem mapColoring) {
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
    }

    protected void checkSolvedZebra(ConstraintProblem zebra) {
        Variable<Integer> english = zebra.getVariable("English", Integer.class);
        Variable<Integer> redHouse = zebra.getVariable("Red", Integer.class);
        Assert.assertEquals(english.getValue(), redHouse.getValue());

        Variable<Integer> spaniard = zebra.getVariable("Spaniard", Integer.class);
        Variable<Integer> dog = zebra.getVariable("Dog", Integer.class);
        Assert.assertEquals(spaniard.getValue(), dog.getValue());

        Variable<Integer> green = zebra.getVariable("Green", Integer.class);
        Variable<Integer> coffee = zebra.getVariable("Coffee", Integer.class);
        Assert.assertEquals(green.getValue(), coffee.getValue());

        Variable<Integer> irish = zebra.getVariable("Irish", Integer.class);
        Variable<Integer> tea = zebra.getVariable("Tea", Integer.class);
        Assert.assertEquals(irish.getValue(), tea.getValue());

        Variable<Integer> ivory = zebra.getVariable("Ivory", Integer.class);
        Assert.assertEquals(ivory.getValue().intValue(), green.getValue() - 1);

        Variable<Integer> go = zebra.getVariable("Go", Integer.class);
        Variable<Integer> snail = zebra.getVariable("Snail", Integer.class);
        Assert.assertEquals(go.getValue(), snail.getValue());

        Variable<Integer> cricket = zebra.getVariable("Cricket", Integer.class);
        Variable<Integer> yellow = zebra.getVariable("Yellow", Integer.class);
        Assert.assertEquals(yellow.getValue(), cricket.getValue());

        Variable<Integer> milk = zebra.getVariable("Milk", Integer.class);
        Assert.assertEquals(2, milk.getValue().intValue());

        Variable<Integer> nigerian = zebra.getVariable("Nigerian", Integer.class);
        Assert.assertEquals(0, nigerian.getValue().intValue());

        Variable<Integer> judo = zebra.getVariable("Judo", Integer.class);
        Variable<Integer> fox = zebra.getVariable("Fox", Integer.class);
        Assert.assertEquals(1, Math.abs(judo.getValue() - fox.getValue()));

        Variable<Integer> horse = zebra.getVariable("Horse", Integer.class);
        Assert.assertEquals(1, Math.abs(cricket.getValue() - horse.getValue()));

        Variable<Integer> poker = zebra.getVariable("Poker", Integer.class);
        Variable<Integer> orange = zebra.getVariable("Juice", Integer.class);
        Assert.assertEquals(poker.getValue(), orange.getValue());

        Variable<Integer> japanese = zebra.getVariable("Japanese", Integer.class);
        Variable<Integer> polo = zebra.getVariable("Polo", Integer.class);
        Assert.assertEquals(japanese.getValue(), polo.getValue());

        Variable<Integer> blue = zebra.getVariable("Blue", Integer.class);
        Assert.assertEquals(1, Math.abs(nigerian.getValue() - blue.getValue()));

        Variable<Integer> petZebra = zebra.getVariable("Zebra", Integer.class);
        Variable<Integer> drinkGuinness = zebra.getVariable("Guinness", Integer.class);
        Assert.assertEquals(petZebra.getValue(), japanese.getValue());
        Assert.assertEquals(drinkGuinness.getValue(), nigerian.getValue());
    }

}
