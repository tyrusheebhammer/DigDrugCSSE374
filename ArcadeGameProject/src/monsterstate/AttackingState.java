package monsterstate;

import java.awt.Color;

import game.Monster;

public class AttackingState extends AbstractMonsterState {

	public AttackingState(Monster monster, Color color) {
		this.monster = monster;
		this.color = color;
		this.monster.setDirection(0, 0);
	}

	@Override
	public void attack() {
		this.monster.attack();
	}
	
	@Override
	public void checkForChange() {
		if (!this.monster.checkForFiring()) {
			changeState("normal");
		}
	}


}
