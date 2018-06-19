package game;

import java.awt.Color;
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
public class TomatoHead extends GameObject {

	protected DrugWorld world;
	protected double dx, dy;
	private double originalSize = 24;
	protected Point2D originalLocation;
	public int Direction;
	private boolean ghosting;
	private int collisions;
	private boolean isPaused;
	protected int InfCounter;
	private double popRatio;
	private ArrayList<AttackBehavior> attackBehaviors;
	private int shrinkFactor;

	public TomatoHead(double x, double y, DrugWorld dW, Player player) {
		super();
		this.attackBehaviors = new ArrayList<>();
		this.shrinkFactor = 300;
		this.InfCounter = 0;
		this.collisions = 0;
		this.world = dW;
		this.dx = 1;
		this.dy = 0;
		setWorth(100);
		setPopRatio(2.5);
		setSize(24);
		this.originalSize = getSize();
		setDrawPoint(new Point2D.Double(x,y));
		setOriginalLocation(getDrawPoint());
	}

	private void setOriginalLocation(Point2D drawPoint) {
		this.originalLocation = drawPoint;
		
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

	public void setCollisions(int col) {
		this.collisions = col;
	}

	public Point2D getOriginalLocation() {
		return this.originalLocation;
	}

	// USE DECORATOR HERE
	@Override
	public Color getColor() {
		if (!this.ghosting) {
			return Color.MAGENTA;
		}
		return Color.WHITE;
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

	public void attack() {
		for (AttackBehavior a : this.attackBehaviors) {
			a.attack();
		}
	}

	/*
	 * this method is called to move the monster around, dependent on if the monster
	 * is ghosting or not, if ther are any collisions. And if the monster is
	 * ghosting, it will track down the player and then eventually unghost if in a
	 * position where it isn't colliding with anything THIS SHOULD BE A DECORATOR
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
			Point2D drawPoint = getDrawPoint();
			double x = drawPoint.getX();
			double y = drawPoint.getY();
			// doing actual movement
			switch (this.world.isInsideWorld(drawPoint, (int) getSize())) {
			case 'g':
				setDrawPoint(new Point2D.Double(x , y ));
				break;
			case 'x':
				setDrawPoint(new Point2D.Double(x - this.dx * 10, y));
				this.dx *= -1;
				this.collisions++;
				break;
			case 'y':
				setDrawPoint(new Point2D.Double(x, y - this.dy));
				this.dy *= -1;
				this.collisions++;
				break;
			default:
				break;
			}
		}
	}

	/*
	 * checks for collisions with rocks or dirt and prompts the move method to
	 * reverse direction if the it is colliding with anything
	 */
	public boolean checkForCollision() {

		try {
			if (this.world.nearestDirt(getCenterPoint()) != null) {
				DirtBlock block = this.world.nearestDirt(getCenterPoint());
				Rock rock = this.world.nearestRock(getCenterPoint());
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

	public void print(String arg) {
		System.out.println(arg);
	}
	@Override
	public void moveTo(Point2D point) {
		super.moveTo(point);
		setGhosting(false);
		setPaused(false);
	}

	@Override
	public void die() {
		this.world.removeMonster(this);
		this.world.setPlayerScore(getWorth());
	}

	public void resetAttacks() {
		for (AttackBehavior a : this.attackBehaviors) {
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
	 * Shrinks the monster at a set rate based off it's original size. This only
	 * occurs if the monster has been inflated
	 */
	public void shrink() {
		double size = getSize();
		Point2D drawPoint = getDrawPoint();
		double x = drawPoint.getX();
		double y = drawPoint.getY();
		if (size >= this.originalSize) {
			setSize(size - getOriginalSize() / this.shrinkFactor);
			setDrawPoint(new Point2D.Double(x + ((getOriginalSize() / this.shrinkFactor) / 2),
					y + (getOriginalSize() / this.shrinkFactor) / 2));
			setPaused(true);
		}
		if (size <= this.originalSize)
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
	 * this class uses a little bit of geometry to create player tracking. The basic
	 * idea is that there are two components to the location of the player, the x
	 * and y. These components are added together to get a total and then ratios are
	 * created by dividing each component of the locaiton by the total. Since the
	 * additions of these two fractions always equals 1, the length of the vector of
	 * the monsters movement is always the same and the monster won't accelerate or
	 * decelerate, creating a smooth animation of tracking
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
		setSize(getSize() + 15);
		setPaused(true);
		Point2D drawPoint = getDrawPoint();
		setDrawPoint(new Point2D.Double(drawPoint.getX() - 15, drawPoint.getY() - 15));
		if (getSize() >= getOriginalSize() * this.popRatio) {
			die();
		}
	}

	public void reset() {
		setDrawPoint(getOriginalLocation());
		setGhosting(false);
		resetAttacks();
	}

	public void setShrinkFactor(int shrinkFactor) {
		this.shrinkFactor = shrinkFactor;

	}
	
	/*
	 * @TODO
	 */
	public void setInflateFactor(int inflate) {}

}
