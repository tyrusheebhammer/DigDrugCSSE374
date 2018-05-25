import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.List;

import javax.swing.JComponent;

/*
 * this class handles drawing of the game objects, the 
 * component has a thread that updates the objects based
 * off of their assigned position and their color and shape
 * all objects that need to be drawn by the drug component 
 * implement the Drawable interface, allowing for easy classification.
 * --Drawables include: Player, Monsters, background, bonus object, rocks and dirt
 */
public class DrugComponent extends JComponent {

	private DrugWorld DW;

	private static final int FRAMES_PER_SECOND = 30;
	private static final long REPAINT_INTERVAL_MS = 1000 / FRAMES_PER_SECOND;

	public DrugComponent() {
		//this runnable repaints the objects that are considered drawable
		Runnable repainter = new Runnable() {
			@Override
			public void run() {
				// Periodically asks Java to repaint this component
				try {
					while (true) {
						Thread.sleep(REPAINT_INTERVAL_MS);
						repaint();

					}
				} catch (InterruptedException exception) {
					// Stop when interrupted
				}
			}
		};
		new Thread(repainter).start();
	}

	/*
	 * paint component gets the tools for drawing the objects
	 * and sends the objects to the drawDrawable function
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponents(g);
		Graphics2D g2 = (Graphics2D) g;
		List<Drawable> drawableParts = this.DW.getDrawableParts();
		drawDrawable(g2, this.DW);
		for (Drawable d : drawableParts) {
			drawDrawable(g2, d);
		}
	}

	//uses the graphics to draw the drawable objects
	private static void drawDrawable(Graphics2D g2, Drawable d) {
		Color color = d.getColor();
		Shape shape = d.getShape();
		g2.setColor(color);
		g2.fill(shape);
		g2.setColor(Color.BLACK);
		g2.draw(shape);

	}


	public void setDrugWorld(DrugWorld DW) {
		this.DW = DW;
	}

}
