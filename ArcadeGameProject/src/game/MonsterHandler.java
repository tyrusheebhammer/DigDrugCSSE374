package game;

import java.awt.geom.Point2D;

import deathcommands.DeathCommand;

public interface MonsterHandler {
	public void remove(DeathCommand command);

	public void addMonster(int code, Point2D location);
}
