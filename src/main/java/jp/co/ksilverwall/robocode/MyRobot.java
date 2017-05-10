package jp.co.ksilverwall.robocode;

import robocode.ScannedRobotEvent;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.PI;

/**
 * Main Robot class
 */
public class MyRobot extends robocode.AdvancedRobot {
    private static final double MAX_RADAR_ROTATE = PI / 4;
    private BattleField battleField;
    private Map<String, EnemyStatus> enemyList;
    private Controller controller = new Controller();

    private Position getPosition() {
        return new Position(getX(), getY());
    }

    double getGunAngle() {
        return (PI / 2 - getGunHeadingRadians());
    }

    @Override
    public void run() {
        battleField = new BattleField(getBattleFieldWidth(), getBattleFieldHeight());
        enemyList = new HashMap<>();
        while (true) {
            IAction[] actionList = controller.getActionList(new MyRobotStatus(getEnergy(), getPosition(), getGunHeat(), getGunAngle()), enemyList);
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
