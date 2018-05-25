import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/*
 * this is the general class for monsters that all 
 * of the non-boss monsters are based from. This 
 * monster moves side to side and after
 * a certain number of collisions, the monster
 * turns into a ghost and chases down the player 
 */
public class TomatoHead implements Temporal, Findable, Drawable {

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

	public TomatoHead(double x2, double y2, DrugWorld dW, Player player) {
		this.InfCounter = 0;
		this.collisions = 0;
		this.x = x2;
		this.y = y2;
		this.world = dW;
		this.originalLocation = new Point2D.Double(this.x, this.y);
		this.centerPoint = new Point2D.Double(this.x, this.y);
		this.ghosting = false;
		this.dx = 1;
		this.dy = 0;
		setWorth(100);
		setPopRatio(2.5);
	}

	public void setPopRatio(double popRatio) {
		this.popRatio = popRatio;
	}
	public double getPopRatio() {
		return popRatio;
	}
	public void setWorth(int worth) {
		this.worth = worth;
	}

	public int getWorth() {
		return worth;
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

		return new Rectangle2D.Double(this.x, this.y, size, size);
	}

	@Override
	public Point2D getCenterPoint() {
		// TODO Auto-generated method stub.
		return this.centerPoint;
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
			if (!ghosting) {
				if (Math.abs(this.dx) != 1)
					this.dx = 1;
				this.dy = 0;
				if (collisions >= 5)
					setGhosting(true);
				if (checkForCollision()) {
					this.dx *= -1;

					collisions++;
				}
			} else {
				if (checkForCollision())
					collisions++;
				else if (collisions >= 1000) {
					exitGhost();
				}

				this.dx = getDirectionOfPlayer().getX();
				this.dy = getDirectionOfPlayer().getY();

			}

			// doing actual movement
			switch (this.world.isInsideWorld(centerPoint, (int) size)) {
			case 'g':
				this.x += dx;
				this.y += dy;
				break;
			case 'x':
				this.x -= dx * 10;
				this.dx *= -1;
				this.collisions++;
				break;
			case 'y':
				this.y -= dy;
				this.dy *= -1;
				this.collisions++;
				break;
			}

		}
		this.centerPoint = new Point2D.Double(this.x + 15, this.y + 15);
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

	@Override
	public void timePassed() {
		move();
		checkForPlayerKill();
		shrink();

	}

	public void setOriginalSize(double originalSize) {
		this.originalSize = originalSize;
	}

	public double getOriginalSize() {
		return originalSize;
	}

	/*
	 * Shrinks the monster at a set rate based off it's
	 * original size. This only occurs if the monster 
	 * has been inflated
	 */
	public void shrink() {
		if (this.size >= originalSize) {
			this.size -= getOriginalSize() / 300;
			this.x += (getOriginalSize() / 300) / 2;
			this.y += (getOriginalSize() / 300.0) / 2;
			this.isPaused = true;
		}
		if (this.size <= originalSize)
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
	 */
	public Point2D getDirectionOfPlayer() {
		double distX = world.getPlayer().getCenterPoint().getX() - this.getCenterPoint().getX();
		double distY = world.getPlayer().getCenterPoint().getY() - this.getCenterPoint().getY();
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
		size += 15;
		this.x -= 15 / 2;
		this.y -= 15 / 2;
		if (getSize() >= getOriginalSize() * popRatio) {
			die();
		}
	}
	
	public void reset() {
		this.x = this.originalLocation.getX();
		this.y = this.originalLocation.getY();
		this.ghosting = false;
	}

}
