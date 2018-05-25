import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/*
 * this class holds the information regarding the dirt blocks and is drawn
 * by the drug component class. If the player intersects the dirt, then the
 * dirt is considered "Dug" and dies, and gives the player points
 */
public class DirtBlock implements Drawable, Temporal, Findable {
	private Color color = new Color(145, 112, 33);
	private static final int SIZE = 27;
	private int x, y;
	private Point2D centerPoint;
	private DrugWorld world;

	public DirtBlock(int x, int y, DrugWorld world) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.centerPoint = new Point2D.Double(x + SIZE / 2, y + SIZE / 2);

	}

	public void setColor(Color c) {
		this.color = c;

	}

	public double getX() {
		return this.centerPoint.getX();
	}

	public double getY() {
		return this.centerPoint.getY();
	}

	@Override
	public Point2D getCenterPoint() {
		// TODO Auto-generated method stub.
		return this.centerPoint;
	}

	//
	@Override
	public void die() {
		this.world.removeBlock(this);

	}

	@Override
	public void timePassed() {
		// TODO Auto-generated method stub.

	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub.
		return this.color;
	}

	public static int getSize() {
		return SIZE;
	}

	@Override
	public Shape getShape() {
		return new Rectangle2D.Double(this.x, this.y, SIZE, SIZE);

	}

	@Override
	public boolean getInteractable() {
		// TODO Auto-generated method stub.
		return true;
	}

	@Override
	public void moveTo(Point2D point) {
		// ignore

	}

}