package net.viperfish.ai.localSearch;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class RandomCollection<E> {
    private final NavigableMap<Double, E> map = new TreeMap<>();
    private final Random random;
    private double total = 0;

    public RandomCollection() {
        this(new Random());
    }

    public RandomCollection(Random random) {
        this.random = random;
    }

    public void add(double weight, E result) {
        total += weight;
        map.put(total, result);
    }

    public E next() {
        double value = random.nextDouble() * total;
        try {
            return map.higherEntry(value).getValue();
        } catch (NullPointerException np) {
            System.out.println();
            return null;
        }
    }
}