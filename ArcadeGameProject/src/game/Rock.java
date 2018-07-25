package game;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import deathcommands.RockDeath;

/*
 * this class represents the falling rock
 * in the game. When the player digs 3 dirt 
 * directly below the rock, the rock goes into 
 * falling mode and kills anything directly under it,
 * including monsters.
 */
public class Rock extends GameObject {

	private static final int SIZE = 72;
	private double x, y, dy;
	private Point2D centerPoint;
	private DrugWorld world;
	private static final Color COLOR = Color.GRAY;
	private ArrayList<DirtBlock> blocksBelowMe = new ArrayList<>();
	private boolean falling;

	public Rock(double x, double y, DrugWorld world) {
		this.world = world;
		this.x = x - 23;
		this.y = y - 23;
		this.dy = -2;
		this.centerPoint = new Point2D.Double(x + SIZE / 2, y + SIZE / 2);
		setWorth(10);
	}

	/*
	 * if the rock is not falling and has an empty list of dirt
	 * the rock will get the 3 dirt blocks below, if it is
	 * falling, it will locate the nearest monsters or player
	 * and crush it if the rock hits it. 
	 */
	public void getBlocksBelowMe() {
		try {
			if (blocksBelowMe.size() == 0 && !falling) {
				for (int i = 0; i < 3; i++) {
					blocksBelowMe.add(this.world.nearestDirt(new Point2D.Double((x) + SIZE * i / 2, (y) + SIZE)));
				}
			}
			if (falling) {

				this.world.checkForPlayerKill(getShape());
				Monster monster = this.world.nearestMonster(centerPoint);
				if (this.getShape().intersects((Rectangle2D) monster.getShape())) {
					monster.die();
					
				}
			}
			checkForFall();
		} catch (NullPointerException e) {

		} catch(ClassCastException e){
			
		}
	}

	
	public void move() {
		this.y += dy;
		this.dy += 0.05;
		this.centerPoint = new Point2D.Double(x + SIZE / 2, y + SIZE / 2);
	}

	@Override
	public Point2D getCenterPoint() {
		// TODO Auto-generated method stub.
		return this.centerPoint;
	}

	@Override
	public void die() {
		this.world.remove(new RockDeath(this));

	}

	/*
	 * this method checks to see if the world 
	 * list of dirt still contains the dirt that
	 * is holding up the rock. If the list 
	 * does not contain those dirt, then the rock will 
	 * begin its descent
	 */
	private void checkForFall() {
		int missingCount = 0;
		for (DirtBlock db : blocksBelowMe) {
			if (!world.getDirt().contains(db)) {
				missingCount++;
			}
		}

		if (missingCount == 3) {
			this.falling = true;
		}
	}

	@Override
	public void timePassed() {
		getBlocksBelowMe();
		if (this.falling) {
			move();
		}
		if (this.y > 729 + SIZE) {
			die();
		}

	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub.
		return Rock.COLOR;
	}

	@Override
	public Shape getShape() {
		// TODO Auto-generated method stub.
		return new Rectangle2D.Double(this.x, this.y, SIZE, SIZE);
	}

	@Override
	public void moveTo(Point2D point) {
		// ignore

	}

}
