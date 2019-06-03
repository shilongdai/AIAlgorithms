package net.viperfish.ai.localSearch;

import net.viperfish.ai.classicSearch.GoalTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticSearch<S extends GeneticState> implements LocalSearch<S> {

    private double mutationChance;

    public GeneticSearch(double mutationChance) {
        this.mutationChance = mutationChance;
    }

    @Override
    public S solve(Iterable<S> initialStates, ObjectiveFunction<S> objectiveFunction, GoalTester<S> goalTester) {
        List<S> population = new ArrayList<>();
        Random rand = new Random();
        initialStates.forEach(population::add);
        while (true) {
            RandomCollection<S> selector = new RandomCollection<>(rand);
            for (S s : population) {
                if (goalTester.goalReached(s)) {
                    return s;
                }
                selector.add(objectiveFunction.evaluate(s), s);
            }
            int k = population.size();
            population.clear();
            for (int i = 0; i < k; ++i) {
                S x = selector.next();
                S y = selector.next();
                GeneticState offspring = x.reproduce(y);
                if (rand.nextDouble() < mutationChance) {
                    offspring.mutate();
                }
                population.add((S) offspring);
            }
        }
    }
}
