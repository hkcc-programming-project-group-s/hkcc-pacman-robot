package hkccpacmanrobot.robot.core;

public class ObstacleMapUnit {
	public long x, y;

	public ObstacleMapUnit(long x, long y) {
		this.x = x;
		this.y = y;

	}

	public ObstacleMapUnit(double x, double y) {
		this.x = Math.round(x / 5);
		this.y = Math.round(y / 5);
	}
}
