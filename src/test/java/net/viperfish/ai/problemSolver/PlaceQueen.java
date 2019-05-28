package net.viperfish.ai.problemSolver;

public class PlaceQueen implements Action<Simplified5Queen> {

    private int x;
    private int y;

    public PlaceQueen(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Simplified5Queen predict(Simplified5Queen current) {
        return new Simplified5Queen((Simplified5Queen) current, x, y);
    }

    @Override
    public Simplified5Queen execute(Simplified5Queen current) throws Exception {
        return predict(current);
    }

    @Override
    public double cost() {
        return 1;
    }
}
