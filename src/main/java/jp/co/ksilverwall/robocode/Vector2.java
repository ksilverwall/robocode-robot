package jp.co.ksilverwall.robocode;

import static java.lang.Math.atan;
import static java.lang.Math.sqrt;

class Vector2 {
    public double x;
    public double y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static double dot(Vector2 a, Vector2 b) {
        return a.x * b.x + a.y * b.y;
    }

    public double getLength() {
        return sqrt(x * x + y * y);
    }

    public double getAngle() {
        return atan(x / y);
    }

    public double getDistance(Vector2 dest) {
        double deltaX = x - dest.x;
        double deltaY = y - dest.y;
        return sqrt(deltaX * deltaX + deltaY * deltaY);
    }
}
