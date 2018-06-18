package combat;

import game.Bomb;
import game.DrugWorld;
import game.TomatoHead;

public class ShootMissile implements AttackBehavior {

	private int timeSinceLastShot;
	private TomatoHead monster;
	private DrugWorld world;

	public ShootMissile(DrugWorld world, TomatoHead monster) {
		this.world = world;
		this.monster = monster;

	}

	@Override
	public void attack() {
		int j = (int) (400 + Math.random() * 10000);

		this.timeSinceLastShot++;
		if (this.timeSinceLastShot > j && !monster.getGhosting() && !monster.checkForCollision()
				&& !monster.getPaused()) {
			this.timeSinceLastShot = 0;
			this.world.monstersToAdd.add(new Bomb(monster.getDirectionOfPlayer(), monster.getCenterPoint(), this.world,
					this.monster.getPlayer()));
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub.
		this.timeSinceLastShot = 0;
	}

}
