package game;
import java.awt.Color;
import java.awt.geom.Point2D;

import combat.ShootMissile;

/*
 * this class is another monster variant that shoots out rocks
 * that expand in size as they go 
 */
public class BombingTomatoHead extends Monster {

	public BombingTomatoHead(double x, double y, DrugWorld dW) {
		setWorld(dW);
		setCollisions(0);
		
		setDirection(1,0);
		
		Color[] colors = {Color.GRAY, Color.MAGENTA, Color.MAGENTA, Color.RED};
		initializeStates(colors);
		
		setWorth(150);
		setPopRatio(2.5);
		setSize(36);
		
		setOriginalSize(getSize());
		setDrawPoint(new Point2D.Double(x, y));
		setOriginalLocation(getDrawPoint());
		
		super.addAttackBehavior(new ShootMissile(dW, this.getDirectionOfPlayer()));
		setName("Bomber ");
	}




}
