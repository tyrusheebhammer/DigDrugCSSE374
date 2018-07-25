package combat;

import java.awt.geom.Point2D;

import game.Bomb;
import game.DrugWorld;

public class ShootMissile extends AbstractAttack {

	private int timeSinceLastShot;

	public ShootMissile(DrugWorld world,Point2D playerDirection) {
		this.playerDirection = playerDirection;
		this.world = world;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub.
		this.timeSinceLastShot = 0;
	}

	@Override
	public void attack(Point2D centerpoint) {
		int j = (int) (400 + Math.random() * 10000);

		this.timeSinceLastShot++;
		if (this.timeSinceLastShot > j) {
			this.timeSinceLastShot = 0;
			this.world.monstersToAdd.add(new Bomb(this.playerDirection, centerpoint, this.world));
		}

	}

}
