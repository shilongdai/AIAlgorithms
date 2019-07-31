package net.viperfish.ai.csp;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class IntegerVariable extends Variable<Integer> {
    public IntegerVariable(Integer value, int lowerBound, int upperBound) {
        super(value, generateSet(lowerBound, upperBound));
    }

    private static Collection<Integer> generateSet(int lower, int upper) {
        Set<Integer> result = new HashSet<>();
        for (int i = lower; i < upper; ++i) {
            result.add(i);
        }
        return result;
    }
}
