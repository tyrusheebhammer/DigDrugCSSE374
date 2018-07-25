package game;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import combat.ShootMissile;
import combat.Spew;

/*
 * this class is the boss level for the 
 * Boss. The boss is the final level of the game
 * and awards 1500 points upon his death
 * the boss shoots out fire and bombs, so essentially it 
 * is a more powerful puker and bomber. The 
 * boss shoots projectiles more often
 */
public class UnstableBoss extends Monster {
	public UnstableBoss(double x, double y, DrugWorld dW) {
		setCollisions(0);
		setWorld(dW);
		setDirection(0,0);
		
		setWorth(1500);
		setPopRatio(7);
		setSize(100);
		
		setOriginalSize(getSize());
		setDrawPoint(new Point2D.Double(x, y));
		setOriginalLocation(getDrawPoint());
		
		addAttackBehavior(new Spew(dW, this.getDirectionOfPlayer()));
		addAttackBehavior(new ShootMissile(dW, this.getDirectionOfPlayer()));
		setShrinkFactor(1200);
		setName("Boss ");
	}

	@Override
	public void timePassed() {
		moveTo(new Point2D.Double(getDrawPoint().getX() + getDirectionOfPlayer().getX() / 5,
				getDrawPoint().getY() + getDirectionOfPlayer().getY() / 5));
		checkForPlayerKill();
		attack();
		shrink();
	}

	@Override
	public Color getColor() {
		return new Color((int) (155 + Math.random() * 99), (int) (155 + Math.random() * 99),
				(int) (155 + Math.random() * 99));

	}

	@Override
	public Shape getShape() {
		// TODO Auto-generated method stub
		return new Rectangle2D.Double(getDrawPoint().getX(), getDrawPoint().getY(), getSize(), getSize());
	}

}
