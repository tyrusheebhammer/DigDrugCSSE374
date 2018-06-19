package game;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import combat.AttackBehavior;
import combat.Explode;

/*
 * this class extends the puke class, it is the
 * bomb that is shot out from the BombingTomatoHead 
 * monster 
 */
public class Bomb extends Puke {
	private Color color;
	private double MaxSize;
	private DrugWorld world;
	private double tempSize;

	// Creates a ball that fires towards the player through objects, exploding once
	// reaching
	// a certain size, creating a puke blast in that area that does respond to
	// blocks.

	public Bomb(Point2D direction, Point2D location, DrugWorld dW) {
		super(direction, location, dW, 1, 1);
		// TODO Auto-generated constructor stub.
		this.color = new Color((int) (50 + Math.random() * 204), (int) (50 + Math.random() * 204), 0);
		this.MaxSize = (Math.random() * 50 + 40);
		this.world = dW;
		this.tempSize = 30;
		setDirection(2 * direction.getX() + direction.getX() * (0.5 - Math.random()),
				2 * direction.getY() + direction.getY() * (0.5 - Math.random()));
		setWorth(15);
		setName("Bomb ");
	}

	// THIS COULD PROBABLY IMPLEMENT A NEW TYPE OF ATTACK, CALLED EXPLODE, SINCE
	// OTHER
	// MONSTERS USE THIS WHEN THEY DIE
	@Override
	public void timePassed() {
		// This "monster" moves continuously in the direction of the player from when
		// it was created until it reaches its randomly assigned max size. It will then
		// explode
		// into a puke barage that explodes everywhere.
		// WE PROBABLY WANT TO CHECK TO SEE IF THE PLAYER HAS BEEN HIT THROUGH SOME
		// OTHER MEANS
		move();
		checkForPlayerKill();
		this.tempSize += .09;
		if (this.MaxSize <= this.tempSize) {
			AttackBehavior explosion = new Explode(this.world, this);
			explosion.attack();
			super.die();
		}
	}

	@Override
	public Shape getShape() {
		return new Ellipse2D.Double(getCenterPoint().getX(), getCenterPoint().getY(), this.tempSize, this.tempSize);

	}


	@Override
	public void move() {
		moveTo(new Point2D.Double(getDrawPoint().getX() + getDirection().getX(),
				getDrawPoint().getY() + getDirection().getY()));
	}

	@Override
	public void reset() {
		super.die();
	}
}
