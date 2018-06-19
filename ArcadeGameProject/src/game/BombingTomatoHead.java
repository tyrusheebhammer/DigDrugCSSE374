package game;
import java.awt.Color;

import combat.ShootMissile;

/*
 * this class is another monster variant that shoots out rocks
 * that expand in size as they go 
 */
public class BombingTomatoHead extends TomatoHead {
	private Color color;

	public BombingTomatoHead(double x, double y, DrugWorld dW) {
		super(x, y, dW);
		this.color = Color.PINK;
		setSize(36);
		setOriginalSize(36);
		setWorth(150);
		super.addAttackBehavior(new ShootMissile(dW, this));
		setName("Bomber ");
	}

	@Override
	public Color getColor() {
		if (!getGhosting()) {
			return this.color;
		}
		return Color.LIGHT_GRAY;
	}



}
