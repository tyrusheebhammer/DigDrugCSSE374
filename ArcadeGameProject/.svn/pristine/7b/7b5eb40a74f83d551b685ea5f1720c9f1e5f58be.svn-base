import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * this class handles the game commands, such as
 * pausing, nextlevel previouslevel, shooting the
 * weapon and player movement
 */
public class KeyboardButtonListener implements KeyListener {

	private DrugWorld world;
	

	public KeyboardButtonListener(DrugWorld world) {
		this.world = world;
		System.out.println("Listener created");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_P) {
			world.togglePaused();

		}
		if (key == KeyEvent.VK_U) {
			this.world.nextLevel(false);
			return;
		}
		if (key == KeyEvent.VK_D) {
			this.world.previousLevel();
			return;
		}
		if (key == KeyEvent.VK_C) {
			this.world.getPlayer().isPaused = true;
			this.world.fireWeapon();
			return;
		}
		

		if (world.getPlayer() != null )
			world.getPlayer().keyPressed(e);

	}

	@Override
	public void keyReleased(KeyEvent e) {
		world.getPlayer().keyReleased(e);

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub.

	}

}
