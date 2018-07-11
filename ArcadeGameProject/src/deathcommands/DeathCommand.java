package deathcommands;

import game.DeathHandler;

public abstract class DeathCommand {
	protected DeathHandler handler;

	public void injectHandler(DeathHandler handler) {
		this.handler = handler;
	}

	public abstract void kill();
}
