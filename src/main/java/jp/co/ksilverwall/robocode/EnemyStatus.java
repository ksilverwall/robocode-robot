package jp.co.ksilverwall.robocode;

import robocode.ScannedRobotEvent;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

class EnemyStatus {
    public final double energy;
    /**
     * Absolute Position
     */
    public final Position position;
    /**
     * Absolute Velocity
     */
    public final Velocity velocity;

    public EnemyStatus(double energy, Position position, Velocity velocity) {
        this.energy = energy;
        this.position = position;
        this.velocity = velocity;
    }

    @Override
    public String toString() {
        return String.format("[e:%.5f,p:%s,v:%s]", energy, position.toString(), velocity.toString());
    }

    public static EnemyStatus parse(ScannedRobotEvent event, Position base) {
        double radius = event.getBearingRadians();
        double distance = event.getDistance();
        Position p = new Position(distance * sin(radius), distance * cos(radius)).add(base);

        double velocity = event.getVelocity();
        double heading = event.getHeadingRadians();
        Velocity v = new Velocity(velocity * sin(heading), velocity * cos(heading));

        double energy = event.getEnergy();

        return new EnemyStatus(energy, p, v);
    }
}
