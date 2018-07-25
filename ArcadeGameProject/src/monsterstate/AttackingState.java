package monsterstate;

import java.awt.Color;

import game.TomatoHead;

public class AttackingState extends AbstractMonsterState {

	public AttackingState(TomatoHead monster, Color color) {
		this.monster = monster;
		this.color = color;
		this.monster.setDirection(0, 0);
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
		// TODO Auto-generated method stub.
		
	}

	@Override
	public void inflate() {
		// TODO Auto-generated method stub.
		
	}

}
