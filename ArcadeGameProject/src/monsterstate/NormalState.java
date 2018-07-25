package monsterstate;

import java.awt.Color;

import game.TomatoHead;

public class NormalState extends AbstractMonsterState {
	int collisions;


	public NormalState(TomatoHead monster, Color color) {
		this.monster = monster;
		this.color = color;
		this.monster.setDirection(1, 0);
		this.collisions = 0;
	}
	
	@Override
	public void timePassed() {
		this.monster.checkForPlayerKill();

	}

	@Override
	public void move() {
		// TODO Auto-generated method stub.

	}

	@Override
	public void attack() {
		this.monster.attack();
		if(this.monster.checkForFiring()) {
			try {
				this.monster.setState("attacking");
			} catch (Exception exception) {
				// TODO Auto-generated catch-block stub.
				exception.printStackTrace();
			}
		}
		
	}

	@Override
	public void inflate() {
		// TODO Auto-generated method stub.
		
	}


	
}
