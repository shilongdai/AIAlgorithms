package net.viperfish.ai.csp;

import java.util.Arrays;
import java.util.HashSet;

public class MapColor extends Variable<Color> {

    public MapColor(Color color) {
        super(color, new HashSet<>(Arrays.asList(Color.values())));
    }
}
