package monsterstate;

import java.awt.Color;

import game.TomatoHead;

public class InflatedState extends AbstractMonsterState {

	public InflatedState(TomatoHead monster, Color color) {
		this.monster = monster;
		this.color = color;
		this.monster.setDirection(0, 0);
	}

	@Override
	public void timePassed() {
		// TODO Auto-generated method stub.

	}

	@Override
	public void move() {
		// TODO Auto-generated method stub.

	}


	@Override
	public void inflate() {
		// TODO Auto-generated method stub.
		
	}
	
	public void shrink() {
		
	}

}
