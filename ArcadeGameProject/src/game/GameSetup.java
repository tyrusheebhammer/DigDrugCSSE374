package game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

/*
 * this class is where the game setup is handled.
 * this adds listeners to the frame to be able
 * to control the player, and adds the DrugWorld
 * and DrugComponent classes and passes them to 
 * each other. THis also grabs the player score
 * and the player lives and presents them to the
 * user at the top of the frame
 */
public class GameSetup {
	private static Dimension dimension;
	private JFrame frame;
	private StartingScreenComponent screen;
	private FrameKeyListener FKL;
	private DrugWorld world;

	public GameSetup(JFrame frame, Dimension dimension) {
		GameSetup.dimension = dimension;
		this.frame = frame;
		this.screen = new StartingScreenComponent();

		FKL = new FrameKeyListener(this);
		this.frame.addKeyListener(FKL);

		this.frame.add(screen);
		this.frame.setPreferredSize(dimension);
		this.frame.pack();
		this.frame.setVisible(true);
	}

	/*
	 * this method handles creating the component and world objects keeping
	 * track of the player's score and lives. Also if the player calls a full
	 * reset after dying 3 times, the initialize method creates a new GameSetup
	 * to start over and ask the player if he wants to play again
	 */
	public void initialize() {

		DrugComponent DC = new DrugComponent();
		this.world = new DrugWorld(DC);
		final PlayerInformationComponent PIC = new PlayerInformationComponent();
		
		DC.setDrugWorld(world);
		frame.addKeyListener(new KeyboardButtonListener(world));
		DC.setFocusable(true);
		
		this.frame.add(PIC);
		this.frame.pack();

		frame.setPreferredSize(dimension);
		Runnable tickTock = new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						Thread.sleep(10);
						PIC.repaint();
						if (world.getTotalReset()) {
							frame.dispose();

							GameSetup g = new GameSetup(new JFrame(), dimension);

						}
					}
				} catch (Exception e) {
					// Stop when interrupted
				}
			}
		};
		new Thread(tickTock).start();
		frame.add(DC);
		frame.pack();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public void quit() {
		System.exit(0);
	}

	// handler for key presses. if the player presses q the game quits
	// they press n, creates a new game.
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_N) {
			frame.remove(screen);
			frame.removeKeyListener(FKL);
			initialize();

		}
		if (key == KeyEvent.VK_Q) {
			quit();
		}
	}

	private class GameInstructionComponent extends JComponent {
		protected void paintComponent(Graphics g) {
			
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			
			
		}
	}

	private class GameInstruction {
		public void drawOn(Graphics2D g) {
			Font font = new Font("Arial", Font.BOLD, 30);
			Font font2 = new Font("Arial",Font.PLAIN,15);
			String text1 = "Instructions";
			String[] texts = {"Movement - ArrowKeys","Toggle Pause - P","Shoot Weapon - C","Toggle Shield - Spacebar","Level up - U","Level Down - D"};
			
			
			g.setColor(Color.BLACK);
			Rectangle2D background = new Rectangle2D.Double(972, 0, 200, 949);
			g.fill(background);
			g.setColor(Color.CYAN);
			g.draw(background);
			
			g.setFont(font);
			g.drawString(text1, 980, 50);
			g.setFont(font2);
			for(int i = 0; i < texts.length; i++){
				g.drawString(texts[i], 980, 100+50*i);
			}
		}
	}

	private class PlayerInformationComponent extends JComponent {
		@Override
		protected void paintComponent(Graphics g) {
			
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;

			PlayerInformation PI = new PlayerInformation();
			PI.drawOn(g2, world.getPlayerScore(),
					world.getPlayerLives(), world.getPlayerShieldLeft());
			
			GameInstruction game = new GameInstruction();
			game.drawOn(g2);

		}
	}

	/*
	 * this class shows the lives left, the players score
	 * and shield charge
	 */
	private class PlayerInformation {
		public PlayerInformation() {

		}

		public void drawOn(Graphics2D g, int playerScore,
				int playerLives, double shieldLeft) {
			Font font = new Font("Arial", Font.BOLD, 40);
			String text1 = "Score: " + playerScore;
			String text3 = "Shield: "/* + shieldRecharge */;

			Rectangle2D background = new Rectangle2D.Double(0, 810, 973, 100);
			
			g.setColor(Color.BLACK);
			g.fill(background);

			g.setFont(font);
			g.setColor(Color.CYAN);
			g.draw(background);

			g.drawString(text1, 50, 865);

			for(int i = 0; i<playerLives;i++){
				g.fillRoundRect(325+45*i, 837, 30, 30, 10, 10);
			}
			g.fillRoundRect(550, 833, (int) (shieldLeft*0.3), 40, 25, 25);
		}
	}

	private class StartingScreenComponent extends JComponent {

		/*
		 * Draws the starting screen
		 */
		@Override
		protected void paintComponent(Graphics g) {
			
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;

			StartingScreen screen = new StartingScreen();
			screen.drawOn(g2);
		}
	}

	private class StartingScreen {

		/*
		 * holds information on the start screen when the game initially starts
		 */
		public StartingScreen() {

		}

		public void drawOn(Graphics2D g) {
			Font font = new Font("Arial", Font.BOLD, 40);
			String text1 = "WELCOME TO DIG DRUG";
			String text2 = "Press 'n' for a new game";
			String text3 = "Press 'q' to quit";

			Rectangle2D background = new Rectangle2D.Double(0, 0, 1189, 949);
			g.setColor(Color.BLACK);
			g.fill(background);

			g.setFont(font);
			g.setColor(Color.CYAN);
			g.drawString(text1, 300, 300);

			g.drawString(text2, 300, 400);

			g.drawString(text3, 300, 500);

		}
	}
}
