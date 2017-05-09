package jp.co.ksilverwall.robocode;

import robocode.ScannedRobotEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.PI;
import static java.lang.Math.sqrt;
import static jp.co.ksilverwall.robocode.Vector2.dot;

/**
 * Main Robot class
 */
public class MyRobot extends robocode.AdvancedRobot {
    private static final double MAX_GUN_ROTATE = PI / 4;
    private static final double MAX_RADAR_ROTATE = PI / 4;
    private BattleField battleField;
    private Map<String, EnemyStatus> enemyList;

    private Position getPosition() {
        return new Position(getX(), getY());
    }

    double getGunAngle() {
        return (PI / 2 - getGunHeadingRadians());
    }

    static private IAction[] think(MyRobotStatus robot, Map<String, EnemyStatus> enemyList) {
        ArrayList<IAction> actionList = new ArrayList<>();

        // Basic Targeting
        EnemyStatus targetStatus = null;
        double targetDistance = Double.MAX_VALUE;
        for (String name : enemyList.keySet()) {
            EnemyStatus enemyStatus = enemyList.get(name);
            double distance = enemyStatus.position.getDistance(robot.position);
            if (distance < targetDistance) {
                targetStatus = enemyStatus;
                targetDistance = distance;
            }
        }

        if (robot.gunHeat == 0) {
            // Linear Behavior Inference
            // (1) |Vb| = 20 - 3 * power
            // (2) Vb * t + P == Vt * t + Pt;
            // Then
            // (3) t^2 * (|Vb|^2 - |Vt|^2) - 2 * t * Vt * (Pt - P) - |Pt - P|^2 == 0
            // (4) Vb = Vt + (Pt - P) / t
            int power = 1;
            double bulletSpeed = 20 - 3 * power;
            Position p = targetStatus.position.sub(robot.position);
            double Z = bulletSpeed * bulletSpeed - dot(targetStatus.velocity, targetStatus.velocity);
            double quarterDeterminer = dot(targetStatus.velocity, p) * dot(targetStatus.velocity, p) - Z * dot(p, p);
            if (quarterDeterminer >= 0) {
                // Answer of Equality is Exists
                double min_t = (dot(targetStatus.velocity, p) - sqrt(quarterDeterminer)) / Z;
                Velocity bulletVelocity = targetStatus.velocity.add(p.divByTime(min_t));
                assert (bulletVelocity.getLength() - bulletSpeed == 0.0);
                double bulletAngle = bulletVelocity.getAngle();
                double gunAngle = robot.gunAngle;
                if (Angle.diff(bulletAngle, gunAngle) < MAX_GUN_ROTATE) {
                    actionList.add(new ShotAction(power, bulletAngle));
                }
            }
        }
        return actionList.toArray(new IAction[actionList.size()]);
    }

    @Override
    public void run() {
        battleField = new BattleField(getBattleFieldWidth(), getBattleFieldHeight());
        enemyList = new HashMap<>();
        while (true) {
            IAction[] actionList = think(new MyRobotStatus(getEnergy(), getPosition(), getGunHeat(), getGunAngle()), enemyList);
            for (IAction action : actionList) {
                action.execute(this);
            }
            setTurnRadarRight(Math.toDegrees(MAX_RADAR_ROTATE));
            execute();
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        String name = event.getName();
        EnemyStatus status = EnemyStatus.parse(event, getPosition());
        enemyList.put(name, status);
    }

}
