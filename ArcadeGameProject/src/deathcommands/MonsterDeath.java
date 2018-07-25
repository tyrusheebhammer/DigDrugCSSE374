package deathcommands;
import game.Monster;
public class MonsterDeath extends DeathCommand {
	
	private Monster monster;
	public MonsterDeath(Monster monster) {
		this.monster = monster;
	}
	@Override
	public void kill() {
		this.handler.removeMonster(this.monster);
		this.handler.setPlayerScore(this.monster.getWorth());
	}

}
