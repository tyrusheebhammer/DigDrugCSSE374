package game;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;
/*
 * This class is the bonus score object that is spawned
 * when 2 of the rocks on the level are destroyed. The
 * bonus is eaten when the player's shape intersect this
 * shape. The player gets 500 bonus points
 */
public class BonusScoreObject extends GameObject {
	private double x, y, size;
	private DrugWorld world;
	private Random rand = new Random();
	private Player player;
	private boolean isPaused;
	
	public BonusScoreObject(double x, double y, DrugWorld world) {
		this.size = 20;
		this.x = x - 23;
		this.y = y - 30;
		this.world = world;
		this.player = this.world.getPlayer();
		this.isPaused = false;
		}

	@Override
	public Color getColor() {

		return new Color(rand.nextInt(254), rand.nextInt(254), rand.nextInt(254));

	}

	@Override
	public Shape getShape() {
		return new Ellipse2D.Double(x, y, size, size);
	}

	@Override
	public Point2D getCenterPoint() {
		return this.getCenterPoint();
	}

	@Override
	public void moveTo(Point2D point) {
		// NOT USED

	}

	@Override
	public void die() {

		this.world.removeBonus(this);
		this.world.setPlayerScore(500);


	}

	@Override
	public void timePassed() {
		checkForEaten();

	}
	
	/*
	 * checks for an interaction between the player and the bonus object
	 */
	public void checkForEaten() {
		if (this.getShape().intersects((Rectangle2D) player.getShape())) {
			System.out.println("Player Eats ");
			this.die();
		}
	}
	
	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}
	
	public boolean getPaused() {
		return this.isPaused;
	}

}
