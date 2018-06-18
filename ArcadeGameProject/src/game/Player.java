package game;

import java.awt.Color;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/*
 * this class is what the user controls. The player can move
 * around the map, mine dirt, shoot its weapon to kill
 * monsters, be crushed by rocks and be killed by monsters
 * this class checks for collisions with rocks and dirt
 * and movement is handled by key presses
 */
public class Player implements Findable, Temporal, Drawable {

	private DrugWorld world;
	private Point2D centerPoint;
	private double x, y, dx, dy, shieldLeft;
	public int score, lives;
	private static final int SIZE = 27;
	private Color color = Color.RED;
	private Point2D originalLocation;
	public int Direction;
	public boolean isPaused, totalReset, isShielded;

	public Player(int x, int y, DrugWorld dW) {
		this.shieldLeft = 1000;
		this.lives = 3;
		this.score = 0;
		this.x = x - 23;
		this.y = y - 23;
		this.world = dW;
		this.isPaused = false;
		this.originalLocation = new Point2D.Double(this.x + Player.SIZE / 2 + 12, this.y + Player.SIZE / 2 + 36);
		this.centerPoint = new Point2D.Double(this.x + Player.SIZE / 2, this.y + Player.SIZE / 2);

	}

	/*
	 * uses the world's nearest dirt method to check if the player is going to dig
	 * dirt, and kills the dirt if it is dug, giving player 3 points per block
	 */
	private void checkForCollision() {
		// Add check if being crushed by rock, or hitting an enemy/ enemy
		// projectile
		if (this.world.nearestDirt(this.centerPoint) != null) {
			DirtBlock block = this.world.nearestDirt(this.centerPoint);

			if (block == null)
				return;
			if (this.getShape().intersects((Rectangle2D) block.getShape())) {
				this.score += 3;
				block.die();
			}
		}
	}

	public boolean getShielded() {
		return this.isShielded;
	}

	/*
	 * sets the shield as active or inactive depending on the boolean passed in.
	 * also checks to see if the player has enough charge to reset the shield
	 */
	public void toggleShielded() {
		if (this.shieldLeft >= 500) {
			this.isShielded = !this.isShielded;
		} else {
			this.isShielded = false;
		}
	}

	/*
	 * runs through the shield and decreases the charge if the shield is active and
	 * slowly increases the shield if inactive
	 */
	public void shielded() {
		if (this.isShielded) {
			this.shieldLeft -= 5;
			if (this.shieldLeft <= 50) {
				toggleShielded();
			}
		} else if (this.shieldLeft <= 1000) {
			this.shieldLeft += 0.5;
		}
	}

	/*
	 * checks to see if the player is hitting the rock while it isn't moving
	 */
	public void checkForRockCollision() {
		if (this.world.nearestRock(this.centerPoint) != null) {
			Rock r = this.world.nearestRock(this.centerPoint);
			if (this.getShape().intersects((Rectangle2D) r.getShape())) {
				dealWithRock();
			}
		}
	}

	/*
	 * handles if the player is intersecting the rock, moves the player over
	 */
	public void dealWithRock() {
		if (this.dx > 0) {
			this.x -= 20;
		} else if (this.dx < 0) {
			this.x += 20;
		} else if (this.dy > 0) {
			this.y -= 20;
		} else if (this.dy < 0) {
			this.y += 20;
		}
	}

	public int getLives() {
		return this.lives;
	}

	public double getShieldLeft() {
		return this.shieldLeft;
	}

	public int getScore() {
		return this.score;
	}

	public void reset() {
		moveTo(this.originalLocation);
	}

	@Override
	public Point2D getCenterPoint() {
		// TODO Auto-generated method stub.
		return this.centerPoint;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub.
		if (this.isShielded)
			return Color.CYAN;
		return this.color;
	}

	@Override
	public Shape getShape() {
		// TODO Auto-generated method stub.
		if (getShielded()) {
			return new Rectangle2D.Double(this.x - 6, this.y - 6, SIZE + 12, SIZE + 12);
		}
		return new Rectangle2D.Double(this.x, this.y, SIZE, SIZE);
	}

	public boolean getTotalReset() {
		if (this.totalReset) {
			this.totalReset = false;
			return true;
		}
		return false;
	}

	@Override
	public void die() {
		if (getShielded()) {
			this.shieldLeft -= 70;
			TomatoHead monster = this.world.nearestMonster(this.centerPoint);
			double monstX = monster.getCenterPoint().getX() - this.centerPoint.getX();
			double monstY = monster.getCenterPoint().getY() - this.centerPoint.getY();
			monster.moveTo(new Point2D.Double(monster.getCenterPoint().getX() + monstX / 2,
					monster.getCenterPoint().getY() + monstY / 2));
			monster.Inflate();
			return;
		}
		this.lives--;
		if (this.lives == 0) {
			this.totalReset = true;
		}
		this.shieldLeft = 1000;
		this.world.resetLevel(false);

	}

	public void setPlayerScore(int score) {
		this.score += score;
	}
	/*
	 * handles the movement of the player. and checks to see if the player is inside
	 * the world. if he isn't then move him back into the world;
	 */

	public void move() {
		if (!this.isPaused) {

			switch (this.world.isInsideWorld(this.centerPoint, SIZE)) {
			case 'g':
				this.x += this.dx;
				this.y += this.dy;
				break;
			case 'x':
				this.x -= this.dx * 10;
				break;
			case 'y':
				this.y -= this.dy * 10;
				break;
			}
		}

		this.centerPoint.setLocation(this.x + SIZE / 2, this.y + SIZE / 2);
	}

	/*
	 * Time passed method handles the updating of the player movement
	 */
	@Override
	public void timePassed() {
		checkForCollision();
		checkForRockCollision();
		move();
		shielded();

	}

	@Override
	public boolean getInteractable() {
		// TODO Auto-generated method stub.
		return true;
	}

	public Point2D getOriginalLocation() {
		return this.originalLocation;
	}

	@Override
	public void moveTo(Point2D point) {
		this.x = point.getX() - 23;
		this.y = point.getY() - 23;

	}

	/*
	 * Handles the player's movement through keypresses, but only allows movement in
	 * dimension at a time
	 */
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT) {
			if (this.dy == 0) {
				this.dx = -2;
				this.Direction = 0;
			}
		}

		if (key == KeyEvent.VK_RIGHT) {
			if (this.dy == 0) {
				this.dx = 2;
				this.Direction = 3;
			}
		}

		if (key == KeyEvent.VK_UP) {
			if (this.dx == 0) {
				this.dy = -2;
				this.Direction = 2;
			}
		}

		if (key == KeyEvent.VK_DOWN) {
			if (this.dx == 0) {
				this.dy = 2;
				this.Direction = 1;
			}
		}
		if (key == KeyEvent.VK_SPACE) {
			toggleShielded();
		}
	}

	/*
	 * closes off the movement of the player when the key button is released
	 */
	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			this.dx = 0;
		}

		if (key == KeyEvent.VK_RIGHT) {
			this.dx = 0;
		}

		if (key == KeyEvent.VK_UP) {
			this.dy = 0;
		}

		if (key == KeyEvent.VK_DOWN) {
			this.dy = 0;
		}
		if (key == KeyEvent.VK_C) {
			this.isPaused = false;
		}
	}

}