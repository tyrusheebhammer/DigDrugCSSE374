package combat;

import java.awt.geom.Point2D;

import game.DrugWorld;

public abstract class AbstractAttack implements AttackBehavior {

	protected Point2D playerDirection;
	protected DrugWorld world;

	@Override
	public void reset() {
		// Do nothing unless you need to
	}

	@Override
	public boolean checkForAttack() {
		return false;
	}

	@Override
	public void updatePlayerDirection(Point2D direction) {
		this.playerDirection = direction;
	}
}
