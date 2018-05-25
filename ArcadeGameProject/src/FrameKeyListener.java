import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * this listener is for the frame of the start screen. This sends the
 * frame information on if the player decides to quit, or play
 * the game.
 * 
 */
public class FrameKeyListener implements KeyListener {

	private GameSetup gameSetup;

	public FrameKeyListener(GameSetup gameSetup) {
		this.gameSetup = gameSetup;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		gameSetup.keyPressed(e);
			
			
		}
		
	

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
