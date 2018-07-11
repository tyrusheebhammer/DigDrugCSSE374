package game;
import java.awt.Color;
import java.awt.geom.Point2D;

import deathcommands.DirtDeath;

/*
 * this class holds the information regarding the dirt blocks and is drawn
 * by the drug component class. If the player intersects the dirt, then the
 * dirt is considered "Dug" and dies, and gives the player points
 */
public class DirtBlock extends GameObject {

	public DirtBlock(int x, int y, MonsterHandler world) {
		setWorld(world);
		setDrawPoint(new Point2D.Double(x, y));
		setSize(27);
		setColor(new Color(145, 112, 33));
		setWorth(4);
	}
	
	@Override
	public void die() {
		getWorld().remove(new DirtDeath(this));
	}
}