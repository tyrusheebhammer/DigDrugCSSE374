package combat;

import java.awt.geom.Point2D;

import game.DrugWorld;
import game.Puke;
import game.TomatoHead;

public class Explode implements AttackBehavior {
	private TomatoHead monster;
	private DrugWorld world;

	public Explode(DrugWorld world, TomatoHead monster) {
		this.world = world;
		this.monster = monster;
	}
	@Override
	public void attack() {
		for (int i = 0; i < 100; i++) {
			this.world.monstersToAdd.add(new Puke(new Point2D.Double(0.5 - Math.random(), 0.5 - Math.random()),
					this.monster.getCenterPoint(), this.world, 5, 0.5));
		}

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub.

	}

}
