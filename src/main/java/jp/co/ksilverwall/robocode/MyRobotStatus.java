package jp.co.ksilverwall.robocode;

class MyRobotStatus {
    public final double energy;
    public final Position position;
    public final double gunHeat;
    public final double gunAngle;

    public MyRobotStatus(double energy, Position position, double gunHeat, double gunAngle) {
        this.energy = energy;
        this.position = position;
        this.gunHeat = gunHeat;
        this.gunAngle = gunAngle;
    }
}
