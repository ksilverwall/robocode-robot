package jp.co.ksilverwall.robocode;

class ShotAction implements IAction {
    private int power;
    private double bulletAngle;

    public ShotAction(int power, double bulletAngle) {
        this.power = power;
        this.bulletAngle = bulletAngle;
    }

    @Override
    public void execute(MyRobot robot) {
        double range = Angle.diff(bulletAngle, robot.getGunAngle());
        if(0 < range) {
            robot.setTurnGunLeftRadians(range);
        }
        else if(range < 0) {
            robot.setTurnGunRightRadians(-range);
        }
        robot.setFire(power);
    }
}
