package game;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import combat.AttackBehavior;

/*
 * this is the general class for monsters that all 
 * of the non-boss monsters are based from. This 
 * monster moves side to side and after
 * a certain number of collisions, the monster
 * turns into a ghost and chases down the player 
 */
public class TomatoHead extends Sprite  {

	protected DrugWorld world;
	protected Point2D centerPoint;
	protected double x, y, dx, dy;
	protected double size = 24;
	private double originalSize = 24;
	protected Point2D originalLocation;
	private int worth;
	public int Direction;
	private boolean ghosting;
	private int collisions;
	private boolean isPaused;
	protected int InfCounter;
	private double popRatio;
	private ArrayList<AttackBehavior> attackBehaviors;
	private Player player;
	private int shrinkFactor;

	public TomatoHead(double x, double y, DrugWorld dW, Player player) {
		super();
		this.player = player;
		this.attackBehaviors = new ArrayList<>();
		this.shrinkFactor = 300;
		this.InfCounter = 0;
		this.collisions = 0;
		this.x = x;
		this.y = y;
		this.world = dW;
		this.originalLocation = new Point2D.Double(this.x, this.y);
		this.centerPoint = new Point2D.Double(this.x, this.y);
		this.ghosting = false;
		this.dx = 1;
		this.dy = 0;
		setWorth(100);
		setPopRatio(2.5);
	}

	public Player getPlayer() {
		return this.player;
	}
	
	public void addAttackBehavior(AttackBehavior attack) {
		this.attackBehaviors.add(attack);
	}
	public void setPopRatio(double popRatio) {
		this.popRatio = popRatio;
	}
	public double getPopRatio() {
		return this.popRatio;
	}
	public void setWorth(int worth) {
		this.worth = worth;
	}

	public int getWorth() {
		return this.worth;
	}

	public void setCollisions(int col) {
		this.collisions = col;
	}

	public Point2D getOriginalLocation() {
		return this.originalLocation;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	public boolean getPaused() {
		return this.isPaused;
	}

	@Override
	public Color getColor() {
		if (!this.ghosting) {
			return Color.MAGENTA;
		}
		return Color.WHITE;
	}

	public double getSize() {
		return this.size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public void setGhosting(boolean b) {
		this.ghosting = b;
	}

	public boolean getGhosting() {
		return this.ghosting;
	}

	public int getCollisions() {
		return this.collisions;
	}

	public Point2D getLocation() {
		return this.centerPoint;
	}

	@Override
	public Shape getShape() {

		return new Rectangle2D.Double(this.x, this.y, this.size, this.size);
	}

	@Override
	public Point2D getCenterPoint() {
		// TODO Auto-generated method stub.
		return this.centerPoint;
	}

	public void attack() {
		for(AttackBehavior a: this.attackBehaviors) {
			a.attack();
		}
	}
	/*
	 * this method is called to move the monster around, dependent on if the
	 * monster is ghosting or not, if ther are any collisions. And if the
	 * monster is ghosting, it will track down the player and then eventually
	 * unghost if in a position where it isn't colliding with anything
	 */
	public void move() {
		if (this.isPaused != true) {
			// checking many cases to see what may affect this monster
			if (!this.ghosting) {
				if (Math.abs(this.dx) != 1)
					this.dx = 1;
				this.dy = 0;
				if (this.collisions >= 5)
					setGhosting(true);
				if (checkForCollision()) {
					this.dx *= -1;

					this.collisions++;
				}
			} else {
				if (checkForCollision())
					this.collisions++;
				else if (this.collisions >= 1000) {
					exitGhost();
				}

				this.dx = getDirectionOfPlayer().getX();
				this.dy = getDirectionOfPlayer().getY();

			}

			// doing actual movement
			switch (this.world.isInsideWorld(this.centerPoint, (int) this.size)) {
			case 'g':
				this.x += this.dx;
				this.y += this.dy;
				break;
			case 'x':
				this.x -= this.dx * 10;
				this.dx *= -1;
				this.collisions++;
				break;
			case 'y':
				this.y -= this.dy;
				this.dy *= -1;
				this.collisions++;
				break;
			}

		}
		this.centerPoint = new Point2D.Double(this.x + size/2, this.y + size/2);
	}

	/*
	 * checks for collisions with rocks or dirt and prompts the move method to
	 * reverse direction if the it is colliding with anything
	 */
	public boolean checkForCollision() {

		try {
			if (this.world.nearestDirt(this.centerPoint) != null) {
				DirtBlock block = this.world.nearestDirt(this.centerPoint);
				Rock rock = this.world.nearestRock(this.centerPoint);
				if (this.getShape().intersects((Rectangle2D) block.getShape())
						|| this.getShape().intersects((Rectangle2D) rock.getShape())) {
					return true;
				}

			}

		} catch (NullPointerException e) {

		}

		return false;
	}

	/*
	 * if the monster's shape intersects with the player then the player dies,
	 * unless the player is shielded
	 */
	public void checkForPlayerKill() {
		Player player = this.world.getPlayer();
		if (this.getShape().intersects((Rectangle2D) player.getShape())) {
			player.die();
		}
	}

	@Override
	public void moveTo(Point2D point) {
		this.x = point.getX();
		this.y = point.getY();
		this.centerPoint = point;
		setGhosting(false);
		setPaused(false);
	}

	@Override
	public boolean getInteractable() {
		// TODO Auto-generated method stub.
		return true;
	}

	@Override
	public void die() {
		this.world.removeMonster(this);
		this.world.setPlayerScore(getWorth());
	}

	public void resetAttacks() {
		for(AttackBehavior a: this.attackBehaviors) {
			a.reset();
		}
	}
	@Override
	public void timePassed() {
		move();
		checkForPlayerKill();
		shrink();
		attack();

	}

	public void setOriginalSize(double originalSize) {
		this.originalSize = originalSize;
	}

	public double getOriginalSize() {
		return this.originalSize;
	}

	/*
	 * Shrinks the monster at a set rate based off it's
	 * original size. This only occurs if the monster 
	 * has been inflated
	 */
	public void shrink() {
		if (this.size >= this.originalSize) {
			this.size -= getOriginalSize() / this.shrinkFactor;
			this.x += (getOriginalSize() / this.shrinkFactor) / 2;
			this.y += (getOriginalSize() / this.shrinkFactor) / 2;
			this.isPaused = true;
		}
		if (this.size <= this.originalSize)
			setPaused(false);
	}

	public void exitGhost() {
		this.setGhosting(false);
		this.collisions = -1;
		this.dx = 2;
		this.dy = 0;

	}

	public void setDirection(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public Point2D getDirection() {
		return new Point2D.Double(this.dx, this.dy);
	}

	/*
	 * this class uses a little bit of geometry to create player tracking. The
	 * basic idea is that there are two components to the location of the
	 * player, the x and y. These components are added together to get a total
	 * and then ratios are created by dividing each component of the locaiton by
	 * the total. Since the additions of these two fractions always equals 1,
	 * the length of the vector of the monsters movement is always the same and
	 * the monster won't accelerate or decelerate, creating a smooth animation
	 * of tracking
	 * 
	 * CREATE AN OBSERVER HERE
	 */
	public Point2D getDirectionOfPlayer() {
		double distX = this.world.getPlayer().getCenterPoint().getX() - this.getCenterPoint().getX();
		double distY = this.world.getPlayer().getCenterPoint().getY() - this.getCenterPoint().getY();
		double total = Math.abs(distX) + Math.abs(distY);
		distX /= total;
		distY /= total;

		return new Point2D.Double(distX, distY);
	}

	/*
	 * inflates the monster
	 */
	public void Inflate() {
		this.isPaused = true;
		this.size += 15;
		this.x -= 15 / 2;
		this.y -= 15 / 2;
		if (getSize() >= getOriginalSize() * this.popRatio) {
			die();
		}
		this.centerPoint = new Point2D.Double(this.x, this.y);
	}
	
	public void reset() {
		this.x = this.originalLocation.getX();
		this.y = this.originalLocation.getY();
		this.ghosting = false;
		resetAttacks();
	}

	public void setShrinkFactor(int shrinkFactor) {
		this.shrinkFactor = shrinkFactor;
		
	}



}
