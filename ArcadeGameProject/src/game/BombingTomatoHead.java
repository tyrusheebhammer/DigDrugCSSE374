package game;
import java.awt.Color;

import combat.ShootMissile;

/*
 * this class is another monster variant that shoots out rocks
 * that expand in size as they go 
 */
public class BombingTomatoHead extends TomatoHead {
	private Color color;
	private Player player;

	public BombingTomatoHead(double x2, double y2, DrugWorld dW, Player player) {
		super(x2, y2, dW, player);
		this.color = Color.PINK;
		this.player = player;
		setSize(36);
		setOriginalSize(36);
		setWorth(150);
		super.addAttackBehavior(new ShootMissile(dW, this));
	}

	@Override
	public Color getColor() {
		if (!getGhosting()) {
			return this.color;
		}
		return Color.LIGHT_GRAY;
	}



}
