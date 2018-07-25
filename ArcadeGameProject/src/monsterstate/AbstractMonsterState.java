package monsterstate;

import java.awt.Color;

import game.Monster;

public abstract class AbstractMonsterState implements MonsterState{
	protected Monster monster;
	protected Color color;
	protected boolean paused;
	
	
	public Color getColor() {
		// TODO Auto-generated method stub.
		return this.color;
	}

	


	
	public void setColor(Color color) {
		// TODO Auto-generated method stub.
		this.color = color;
	}

	@Override
	public void attack() {
		//Hook
	}
	
	@Override
	public void move() {
		//Hook
	}
	
	@Override
	public void shrink() {
		//Hook
	}
	
	@Override
	public void inflate() {
		this.monster.setSize(this.monster.getSize() + 15);
		this.monster.resetAttacks();
		changeState("inflated");
	}
	
	//Template method
	@Override
	public void timePassed() {
		if(paused) return;
		this.monster.checkForPlayerKill();
		move();
		checkForChange();
		attack();
		shrink();
	}
	
	public void changeState(String newState) {
		try {
			this.monster.setState(newState);
		} catch (Exception exception) {
			// TODO Auto-generated catch-block stub.
			exception.printStackTrace();
		}
	}

}
