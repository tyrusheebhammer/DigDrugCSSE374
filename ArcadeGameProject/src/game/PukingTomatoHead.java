package game;

import java.awt.Color;

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

public class PukingTomatoHead extends TomatoHead {
	private AttackBehavior explosion;

	public PukingTomatoHead(double d, double e, DrugWorld dW) {
		super(d, e, dW);
		this.explosion = new Explode(dW, this);
		setSize(48);
		setOriginalSize(48);
		setWorth(200);
		addAttackBehavior(new Spew(dW, this));
		setName("Puker ");
	}

	@Override
	public Color getColor() {
		if (!getGhosting()) {
			return Color.YELLOW;
		}
		return Color.LIGHT_GRAY;
	}


//Turn this into a decorator
	@Override
	public void die() {
		this.explosion.attack();
		super.die();

		/*
		 * when moveTo() is called, the monster is essentially reset
		 */
	}


}
