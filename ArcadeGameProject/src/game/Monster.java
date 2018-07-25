package game;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

import combat.AttackBehavior;
import deathcommands.MonsterDeath;
import monsterstate.AttackingState;
import monsterstate.GhostState;
import monsterstate.InflatedState;
import monsterstate.MonsterState;
import monsterstate.NormalState;
import observerpattern.PlayerObserver;

/*
 * this is the general class for monsters that all 
 * of the non-boss monsters are based from. This 
 * monster moves side to side and after
 * a certain number of collisions, the monster
 * turns into a ghost and chases down the player 
 */
public abstract class Monster extends GameObject implements PlayerObserver {

	protected double dx, dy;
	private double originalSize = 24;
	protected Point2D originalLocation;
	public int Direction;
	private boolean ghosting;
	private int collisions;
	private boolean isPaused;
	protected int InfCounter = 0;
	private double popRatio;
	protected ArrayList<AttackBehavior> attackBehaviors;
	private int shrinkFactor = 300;
	private Point2D playerLocation;
	private String name;

	protected HashMap<String, MonsterState> states;

	private MonsterState currentState;

	//Hook
	protected void initializeStates(Color[] stateColors) {
		this.states = new HashMap<>();
		this.states.put("ghosting", new GhostState(this, stateColors[0]));
		this.states.put("normal", new NormalState(this, stateColors[1]));
		this.states.put("attacking", new AttackingState(this, stateColors[2]));
		this.states.put("inflated", new InflatedState(this, stateColors[3]));
		try {
			setState("normal");
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	

	public void setState(String newState) throws Exception {
		if (this.states.get(newState) == null) {
			throw new Exception("Item doesn't exist");
		}

		this.currentState = this.states.get(newState);
	}

	public Point2D getPlayerLocation() {
		if (this.playerLocation == null) {
			return this.getCenterPoint();
		}
		return this.playerLocation;
	}

	public void setName(String name) {
		this.name = name;

	}

	protected void setOriginalLocation(Point2D drawPoint) {
		this.originalLocation = drawPoint;

	}

	public void addAttackBehavior(AttackBehavior attack) {
		if(this.attackBehaviors == null) {
			this.attackBehaviors = new ArrayList<>();
		}
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

	//added a comment
	public final void attack() {
		if(this.attackBehaviors == null) {
			this.attackBehaviors = new ArrayList<>();
		}
		if (!this.ghosting && !this.isPaused && !this.checkForCollision()) {
			for (AttackBehavior a : this.attackBehaviors) {
				a.attack(getCenterPoint());
			}
		}
	}


	/*
	 * this method is called to move the monster around, dependent on if the monster
	 * is ghosting or not, if ther are any collisions. And if the monster is
	 * ghosting, it will track down the player and then eventually unghost if in a
	 * position where it isn't colliding with anything THIS SHOULD BE A DECORATOR
	 */
	public boolean checkForFiring() {
		for (AttackBehavior a : this.attackBehaviors) {
			if (a.checkForAttack()) {
				System.out.println("I am spewing");
				return true;
			}
		}
		return false;
	}

	public void move() {
		if (checkForFiring()) {
			return;
		}
		if (getSize() > getOriginalSize())
			return;
		checkForGhosting();
		movement();

	}

	public void checkForGhosting() {
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
		}
	}

	public void movement() {
		Point2D drawPoint = getDrawPoint();
		double x = drawPoint.getX();
		double y = drawPoint.getY();
		// doing actual movement
		switch (getWorld().isInsideWorld(drawPoint, (int) getSize())) {
		case 'g':
			setDrawPoint(new Point2D.Double(x + this.dx, y + this.dy));
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

	/*
	 * checks for collisions with rocks or dirt and prompts the move method to
	 * reverse direction if the it is colliding with anything
	 */
	public boolean checkForCollision() {
		return getWorld().hasCollided(getCenterPoint(), getShape());
	}

	/*
	 * if the monster's shape intersects with the player then the player dies,
	 * unless the player is shielded
	 */
	public final void checkForPlayerKill() {
		getWorld().checkForPlayerKill(getShape());
	}

	public void print(String arg) {
		System.out.println(arg);
	}

	@Override
	public void moveTo(Point2D point) {
		super.moveTo(point);
		setPaused(false);
	}

	@Override
	public void die() {
		getWorld().remove(new MonsterDeath(this));
	}

	public void resetAttacks() {
		for (AttackBehavior a : this.attackBehaviors) {
			a.reset();
		}
	}

	@Override
	public void timePassed() {
		move(); // Hook
		checkForPlayerKill(); // final
		shrink(); // Hook
		attack(); // final
		checkDeathBehavior(); // Hook
	}

	public void checkDeathBehavior() {
		// Do nothing
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

	@Override
	public String toString() {
		return getName() + getDrawPoint();

	}

	private String getName() {
		// TODO Auto-generated method stub.
		return this.name;
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
		if (this.playerLocation == null) {
			return new Point2D.Double(0, 0);
		}
		double distX = this.playerLocation.getX() - this.getCenterPoint().getX();
		double distY = this.playerLocation.getY() - this.getCenterPoint().getY();
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

	public void updateAttackLocation() {
		for (AttackBehavior a : this.attackBehaviors) {
			try {
				a.updatePlayerDirection(this.getDirectionOfPlayer());
				} catch(NullPointerException e) {
					System.out.println(a + " " + this.getDirectionOfPlayer());
				}
		}
	}

	@Override
	public void updatePlayerLocation(Point2D point) {
		this.playerLocation = point;
		//updateAttackLocation();

	}

}
