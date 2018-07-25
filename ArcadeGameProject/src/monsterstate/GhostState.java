package monsterstate;

import java.awt.Color;

import game.TomatoHead;

public class GhostState extends AbstractMonsterState{
	int collisions;
	
	public GhostState(TomatoHead monster, Color color) {
		this.monster = monster;
		this.color = color;
		this.monster.setDirection(0, 0);
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
	public void inflate() {
		// TODO Auto-generated method stub.
		
	}

}
