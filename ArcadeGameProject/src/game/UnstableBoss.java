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
public class UnstableBoss extends TomatoHead {



	private Player player;

	public UnstableBoss(double x2, double y2, DrugWorld dW, Player player) {
		super(x2, y2, dW, player);
		this.player = player;
		setSize(100);
		setWorth(1500);
		setOriginalSize(100);
		setPopRatio(7);
		addAttackBehavior(new Spew(dW, this));
		addAttackBehavior(new ShootMissile(dW, this));
		setShrinkFactor(1200);
	}

	@Override
	public void timePassed(){
		moveTo(new Point2D.Double(getCenterPoint().getX()+getDirectionOfPlayer().getX()/5,getCenterPoint().getY()+ getDirectionOfPlayer().getY()/5));
		checkForPlayerKill();
		attack();
		shrink();
	}
	
	@Override
	public Color getColor(){
		return new Color((int)(155+Math.random()*99),(int)(155+Math.random()*99),(int)(155+Math.random()*99));
		
	}
	@Override
	public Shape getShape() {
		// TODO Auto-generated method stub
		return new Rectangle2D.Double(getCenterPoint().getX(), getCenterPoint().getY(), getSize(), getSize());
	}

}
