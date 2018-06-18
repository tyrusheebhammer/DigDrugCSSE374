package combat;

import game.DrugWorld;
import game.Puke;
import game.TomatoHead;

public class Spew implements AttackBehavior {

	private int timeSpentSpewing;
	private int timeSinceLastSpew;
	private boolean firing;
	private TomatoHead monster;
	private DrugWorld world;

	// will eventually just be the monster, also will then only be a part of the
	// overall functionality passed

	public Spew(DrugWorld world, TomatoHead monster) {
		this.world = world;
		this.monster = monster;
	}

	@Override
	public void attack() {
		int j = (int) (2000 + Math.random() * 10000);

		if (!this.firing) {
			this.monster.setPaused(false);
			this.timeSinceLastSpew++;
			if (this.timeSinceLastSpew > j && !this.monster.getGhosting() && !this.monster.checkForCollision()) {
				this.firing = true;
				this.monster.setPaused(true);
				this.timeSinceLastSpew = 0;
			}
		}

		else {
			this.monster.setPaused(true);
			this.timeSpentSpewing++;

			this.world.monstersToAdd.add(new Puke(this.monster.getDirectionOfPlayer(), this.monster.getCenterPoint(),
					this.world, this.monster.getPlayer(), 1, 1));

			if (this.timeSpentSpewing > j - 1000) {
				this.firing = false;
				this.monster.setPaused(false);
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
	
	

}
