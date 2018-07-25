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
	private DrugWorld world;
	private Color color;
	private Point2D drawPoint;

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setWorld(DrugWorld world2) {
		this.world = world2;
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
		this.drawPoint = point;
		setCenterPoint(
				new Point2D.Double(this.drawPoint.getX() + this.size / 2, this.drawPoint.getY() + this.size / 2));
	}

	public Point2D getDrawPoint() {
		return this.drawPoint;
	}

	@Override
	public Shape getShape() {
		return new Rectangle2D.Double(this.drawPoint.getX(), this.drawPoint.getY(), this.size, this.size);
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
		setDrawPoint(point);
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
