package game;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class GameObject implements Temporal, Findable, Drawable {
	private Player player;
	private int worth;
	private boolean isPaused;
	private double size;
	private Point2D centerPoint;
	private double x;
	private double y;
	private DrugWorld world;
	private Color color;

	public void setWorld(DrugWorld world) {
		this.world = world;
	}

	public DrugWorld getWorld() {
		return this.world;
	}

	public Player getPlayer() {
		return this.player;
	}

	public void setWorth(int worth) {
		this.worth = worth;
	}

	public int getWorth() {
		return this.worth;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	public boolean getPaused() {
		return this.isPaused;
	}

	public double getSize() {
		return this.size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public void setDrawPoint(Point2D point) {
		this.x = point.getX();
		this.y = point.getY();
		setCenterPoint(new Point2D.Double(this.x + this.size / 2, this.y + this.size / 2));
	}

	@Override
	public Shape getShape() {

		return new Rectangle2D.Double(this.x, this.y, this.size, this.size);
	}

	public void setCenterPoint(Point2D point) {
		this.centerPoint = point;
	}

	@Override
	public Point2D getCenterPoint() {
		// TODO Auto-generated method stub.
		return this.centerPoint;
	}

	@Override
	public void moveTo(Point2D point) {
		this.x = point.getX();
		this.y = point.getY();
		this.centerPoint = point;
	}

	@Override
	public void timePassed() {
		// TODO Auto-generated method stub.
	}

	public void setColor(Color c) {
		this.color = c;

	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub.
		return this.color;
	}

}
