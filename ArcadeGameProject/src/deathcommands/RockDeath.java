package deathcommands;

import game.Rock;

public class RockDeath extends DeathCommand {
	
	private Rock rock;

	public RockDeath(Rock rock) {
		this.rock = rock;
	}

	@Override
	public void kill() {
		this.handler.removeRock(this.rock);
		this.handler.setPlayerScore(this.rock.getWorth());

	}

}
