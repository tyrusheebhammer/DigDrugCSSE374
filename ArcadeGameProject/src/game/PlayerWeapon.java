package game;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/*
 * this class is the weapon that the player uses to kill enemies. 
 * it extends out from the center of the player with a reload time
 * of ~1 second. When a monster is hit, it expands and is stunned
 * when a monster expands four times it dies.
 */
public class PlayerWeapon extends Sprite {
	private int xLength;
	private int yLength;
	private Point2D origin;
	private int direction;
	public boolean dead;
	private Color color = Color.BLUE;
	private DrugWorld dw;

	public PlayerWeapon(Point2D origin, int direction, DrugWorld DW) {
		this.dead = false;
		this.origin = origin;
		this.direction = direction;
		this.xLength = 10;
		this.yLength = 10;
		this.dw = DW;
		Rectangle2D.Double wep = new Rectangle2D.Double(this.origin.getX(), this.origin.getY(), xLength, yLength);

	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub.
		return this.color;
	}

	/*
	 * this method allows for the program to anticipate the largest size of the
	 * monster before hitting the monster
	 */
	public Shape maxShape() {
		if (this.direction == 0) {
			return new Rectangle2D.Double(this.origin.getX() - 100, this.origin.getY(), 100, 15);
		} else if (this.direction == 3) {
			return new Rectangle2D.Double(this.origin.getX(), this.origin.getY(), 100, 15);
		} else if (this.direction == 1) {
			return new Rectangle2D.Double(this.origin.getX(), this.origin.getY(), 15, 100);
		} else if (this.direction == 2) {
			return new Rectangle2D.Double(this.origin.getX(), this.origin.getY() - 100, 15, 100);
		} else {
			return null;
		}
	}

	/*
	 * this get shape method works by dealing with the situations of the
	 * player's direction and drawing the piece in a certain direction
	 */
	@Override
	public Shape getShape() {
		if (this.direction == 0) {
			if (this.xLength < 100) {
				if (this.dead == false) {
					this.origin = new Point2D.Double(this.origin.getX() - 10, this.origin.getY());
					this.xLength += 10;

				}
			} else {
				this.xLength = 0;
				this.yLength = 0;
				this.dead = true;
			}
		}
		if (this.direction == 3) {
			if (this.xLength < 100) {
				if (this.dead == false) {
					this.xLength += 10;
				}
			} else {
				this.xLength = 0;
				this.yLength = 0;
				this.dead = true;
			}
		}
		if (this.direction == 1) {
			if (this.yLength < 100) {
				if (this.dead == false) {
					this.yLength += 10;
				}
			} else {
				this.xLength = 0;
				this.yLength = 0;
				this.dead = true;
			}
		}
		if (this.direction == 2) {
			if (this.yLength < 100) {
				if (this.dead == false) {
					this.origin = new Point2D.Double(this.origin.getX(), this.origin.getY() - 10);
					this.yLength += 10;
				}
			} else {
				this.xLength = 0;
				this.yLength = 0;
				this.dead = true;
			}
		}
		return new Rectangle2D.Double(this.origin.getX(), this.origin.getY(), xLength, yLength);

	}


	@Override
	public void timePassed() {
		// TODO Auto-generated method stub.
		fire();
	}


	/*
	 * checks for intersection of a monster by the weapon, and inflates the
	 * monster if there is an interaction
	 */
	public void fire() {
		for (int i = 0; i < this.dw.monsters.size(); i++) {
			try {
				if (this.maxShape().intersects((Rectangle2D) this.dw.monsters.get(i).getShape())) {
					this.dw.monsters.get(i).Inflate();
					return;
				}
			} catch (Exception ClassCastException) {
				// do nothing
			}

		}

	}

	@Override
	public void die() {
		// TODO Auto-generated method stub.
		
	}

	@Override
	public Point2D getCenterPoint() {
		// TODO Auto-generated method stub.
		return null;
	}

	@Override
	public void moveTo(Point2D point) {
		// TODO Auto-generated method stub.
		
	}

	@Override
	public boolean getInteractable() {
		// TODO Auto-generated method stub.
		return true;
	}

}
