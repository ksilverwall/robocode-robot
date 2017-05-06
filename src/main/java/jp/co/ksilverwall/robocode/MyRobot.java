package jp.co.ksilverwall.robocode;

import robocode.ScannedRobotEvent;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.PI;

/**
 * Main Robot class
 */
public class MyRobot extends robocode.AdvancedRobot {
    private final double MAX_ROTATE_RADER = PI / 4;
    private BattleField battleField;
    private Map<String, EnemyStatus> enemyList;

    private Position getPosition() {
        return new Position(getX(), getY());
    }

    @Override
    public void run() {
        battleField = new BattleField(getBattleFieldWidth(), getBattleFieldHeight());
        enemyList = new HashMap<>();
        while (true) {
            setTurnRadarRight(Math.toDegrees(MAX_ROTATE_RADER));
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
