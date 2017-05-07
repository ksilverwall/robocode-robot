package jp.co.ksilverwall.robocode;

class Position extends Vector2 {
    public Position(double x, double y) {
        super(x, y);
    }

    public Position add(Position base) {
        return new Position(this.x + base.x, this.y + base.y);
    }

    public Position sub(Position base) {
        return new Position(this.x - base.x, this.y - base.y);
    }

    @Override
    public String toString() {
        return String.format("(%.3f,%.3f)", x, y);
    }

    public Velocity divByTime(double t) {
        return new Velocity(this.x / t, this.y / t);
    }
}
