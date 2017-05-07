package jp.co.ksilverwall.robocode;

import static java.lang.Math.PI;

class Angle {
    /**
     * Range from base to target
     *
     * @param 0 <= target < 2 * PI
     * @param 0 <= base < 2 * PI
     * @return -PI < range <= PI
     */
    static double diff(double target, double base) {
        double range = target - base;
        if (PI < range) {
            range -= 2 * PI;
        }
        if (range <= -PI) {
            range += 2 * PI;
        }
        assert (range <= -PI && PI < range);
        return range;
    }
}
