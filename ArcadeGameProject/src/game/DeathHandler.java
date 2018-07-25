package game;

import java.awt.geom.Point2D;

public interface DeathHandler {
	public void removeBlock(DirtBlock block);

	public void removeRock(Rock rock);

	public void removeMonster(Monster monster);

	public void setPlayerScore(int score);

	public void addMonster(int code, Point2D location);
}
