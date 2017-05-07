package jp.co.ksilverwall.robocode;

class Velocity extends Vector2 {
    public Velocity(double x, double y) {
        super(x, y);
    }

    public Velocity add(Velocity base) {
        return new Velocity(this.x + base.x, this.y + base.y);
    }

    public Velocity sub(Velocity base) {
        return new Velocity(this.x - base.x, this.y - base.y);
    }

    @Override
    public String toString() {
        return String.format("(%.3f,%.3f)", x, y);
    }
}
