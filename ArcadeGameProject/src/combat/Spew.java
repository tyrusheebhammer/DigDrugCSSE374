package combat;

import java.awt.geom.Point2D;

import game.DrugWorld;
import game.Puke;

public class Spew extends AbstractAttack {

	private int timeSpentSpewing;
	private int timeSinceLastSpew;
	private boolean firing;
	private final double j = 500 + Math.random() * 1000;
	private final double i = 500 + Math.random() * 1000;
	// will eventually just be the monster, also will then only be a part of the
	// overall functionality passed

	public Spew(DrugWorld world, Point2D playerDirection) {
		this.playerDirection = playerDirection;
		this.world = world;
	}

	@Override
	public void attack(Point2D centerpoint) {
		if (!this.firing) {
			this.timeSinceLastSpew++;
			if (this.timeSinceLastSpew > j) {
				this.firing = true;
				this.timeSinceLastSpew = 0;
			}
		}

		else {
			this.timeSpentSpewing++;

			this.world.monstersToAdd.add(new Puke(this.playerDirection, centerpoint, this.world, 1, 1));
			if (this.timeSpentSpewing > i) {
				this.firing = false;
				this.timeSpentSpewing = 0;

			}

		}

	}

	@Override
	public void reset() {
		this.firing = false;
		this.timeSpentSpewing = 0;
		this.timeSinceLastSpew = 0;

	}

	@Override
	public boolean checkForAttack() {
		// System.out.println(firing);
		return this.firing;
	}

}
