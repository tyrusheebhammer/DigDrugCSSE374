package combat;

import java.awt.geom.Point2D;

import game.DrugWorld;
import game.Puke;

public class Explode extends AbstractAttack{

	public Explode(DrugWorld world) {
		this.world = world;
	}
	@Override
	public void attack(Point2D centerpoint) {
		for (int i = 0; i < 100; i++) {
			this.world.monstersToAdd.add(new Puke(new Point2D.Double(0.5 - Math.random(), 0.5 - Math.random()),
					centerpoint, this.world, 5, 0.5));
		}

	}

}
