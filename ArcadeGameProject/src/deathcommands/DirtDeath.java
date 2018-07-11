package deathcommands;

import game.DirtBlock;

public class DirtDeath extends DeathCommand {
	
	private DirtBlock dirt;
	public DirtDeath(DirtBlock dirt) {
		this.dirt = dirt;
	}
	@Override
	public void kill() {
		this.handler.removeBlock(this.dirt);
		this.handler.setPlayerScore(this.dirt.getWorth());
	}
}
