package jp.co.ksilverwall.robocode;

public class Position {
    public double x;
    public double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Position add(Position base) {
        return new Position(this.x + base.x, this.y + base.y);
    }

    @Override
    public String toString() {
        return String.format("(%.3f,%.3f)", x, y);
    }
}
