package game;

import java.awt.Color;
import java.awt.geom.Point2D;

import combat.AttackBehavior;
import combat.Explode;
import combat.Spew;

/*
 * this class is the drygar mimic that we created
 * this class acts similarly to tomatohead but 
 * occasionally shoots puke from the center of the
 * body. The puking tomatohead also 'aims' at the 
 * player to fire it's projectiles. Similarly to
 * the tomatohead, the puking tomatohead chases
 * the player when it turns into a ghost
 */

public class PukingTomatoHead extends Monster {
	private AttackBehavior explosion;

	public PukingTomatoHead(double x, double y, DrugWorld dW) {
		
		setCollisions(0);
		setWorld(dW);
		setDirection(1,0);
		
		Color[] colors = {Color.LIGHT_GRAY, Color.YELLOW, Color.YELLOW, Color.RED};
		initializeStates(colors);
		
		setWorth(200);
		setSize(48);
		setPopRatio(2.5);
		
		setOriginalSize(getSize());
		setDrawPoint(new Point2D.Double(x, y));
		setOriginalLocation(getDrawPoint());

		setName("Puker ");
		
		addAttackBehavior(new Spew(dW, this.getDirectionOfPlayer()));
		this.explosion = new Explode(dW);
	}



//Turn this into a decorator
	@Override
	public void die() {
		//this.explosion.attack(getCenterPoint());
		super.die();

		/*
		 * when moveTo() is called, the monster is essentially reset
		 */
	}


}
