import java.awt.Color;

/*
 * this class is another monster variant that shoots out rocks
 * that expand in size as they go 
 */
public class BombingTomatoHead extends TomatoHead {
	private Color color;
	private boolean isBomb;
	private int timeSinceLastBomb;
	private Player player;

	public BombingTomatoHead(double x2, double y2, DrugWorld dW, Player player) {
		super(x2, y2, dW, player);
		this.color = Color.PINK;
		this.player = player;
		setSize(36);
		setOriginalSize(36);
		setWorth(150);
	}

	@Override
	public Color getColor() {
		if (!getGhosting()) {
			return this.color;
		}
		return Color.LIGHT_GRAY;
	}

	@Override
	public void timePassed() {
		move();
		checkForPlayerKill();
		shrink();
		spewBomb();

	}

	/*
	 * shoots a bomb out towards the player 
	 */
	public void spewBomb() {
		int j = (int) (400 + Math.random() * 10000);

		timeSinceLastBomb++;
		if (timeSinceLastBomb > j && !getGhosting() && !checkForCollision() && !getPaused()) {
			timeSinceLastBomb = 0;
			this.world.monstersToAdd.add(new Bomb(getDirectionOfPlayer(), getCenterPoint(), this.world, this.player));

		}
	}

}
