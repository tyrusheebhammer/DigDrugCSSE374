package deathcommands;
import game.TomatoHead;
public class MonsterDeath extends DeathCommand {
	
	private TomatoHead monster;
	public MonsterDeath(TomatoHead monster) {
		this.monster = monster;
	}
	@Override
	public void kill() {
		this.handler.removeMonster(this.monster);
		this.handler.setPlayerScore(this.monster.getWorth());
	}

}
