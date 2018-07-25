package monsterstate;

import java.awt.Color;

import game.TomatoHead;

public abstract class AbstractMonsterState implements MonsterState{
	protected TomatoHead monster;
	protected Color color;

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub.
		return this.color;
	}

	@Override
	public void setColor(Color color) {
		// TODO Auto-generated method stub.
		this.color = color;
	}

	@Override
	public void attack() {
		//Default doesn't attack
	}

}
