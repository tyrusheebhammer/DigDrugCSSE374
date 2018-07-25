package game;

import java.awt.Color;
import java.awt.geom.Point2D;

/*
 * this class is contains the information
 * for the projectiles that are fired by
 * the puking tomatohead. The monster 
 * sends in the player direction and the
 * projectile is shot at that player with a little
 * bit of random factor added in to make a spray
 * effect, and also making it tougher for the player
 * Color is also randomly picked between shades of
 * green and red
 */
public class Puke extends Monster {
	private Color color;
	private int lifeCounter = 0;
	private int lifeLimit;


	public Puke(Point2D direction, Point2D location, DrugWorld dW, double spread, double life) {
		setCollisions(0);
		setWorld(dW);
		System.out.println(dW);
		setDirection(2 * direction.getX() + direction.getX() * (0.5 * spread - Math.random() * spread),
				2 * direction.getY() + direction.getY() * (0.5 * spread - Math.random() * spread));
		
		
		setWorth(0);
		setPopRatio(1);
		setSize(5);
		
		setOriginalSize(getSize());
		setDrawPoint(new Point2D.Double(location.getX() - Math.random() * 36, location.getY() - Math.random() * 36));
		setOriginalLocation(getDrawPoint());
		
		setName("Puke ");
		this.lifeLimit = (int) ((Math.random() * 200) * life);
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		getWorld().removeMonster(this);
	}

	/*
	 * Moves the puke according to it's velocity
	 */
	@Override
	public void move() {
		// TODO Auto-generated method stub
		setGhosting(false);
		moveTo(new Point2D.Double(getDrawPoint().getX() + getDirection().getX(),
				getDrawPoint().getY() + getDirection().getY()));

	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return Color.RED;
	}

	/*
	 * The puke can pass through one and a half blocks and also has a life limit
	 * randomly calculated at it's initialization, limiting the projectile from
	 * moving across the map
	 */

	@Override
	public void checkDeathBehavior() {
		this.lifeCounter++;
		if (this.lifeLimit <= this.lifeCounter)
			die();
		if (checkForCollision()) {
			setCollisions(getCollisions() + 1);
		}
		if (getCollisions() > 1) {
			setWorth(0);
			die();
		}
	}

}
