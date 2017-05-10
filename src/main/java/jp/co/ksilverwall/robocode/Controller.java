package jp.co.ksilverwall.robocode;

import java.util.ArrayList;
import java.util.Map;

import static java.lang.Math.PI;
import static java.lang.Math.sqrt;
import static jp.co.ksilverwall.robocode.Vector2.dot;

public class Controller {
    private static final double MAX_GUN_ROTATE = PI / 4;

    public IAction[] getActionList(MyRobotStatus robot, Map<String, EnemyStatus> enemyList) {
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
}
