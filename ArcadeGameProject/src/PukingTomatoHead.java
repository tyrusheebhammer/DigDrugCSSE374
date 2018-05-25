import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

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

	private int timeSinceLastSpew = 0;
	private int timeSpentSpewing = 0;
	private boolean isFire;

	public PukingTomatoHead(int x, int y, DrugWorld dW, Player player) {
		super(x, y, dW, player);
		setPaused(false);
		setSize(48);
		setOriginalSize(48);
		setWorth(200);
	}

	@Override
	public Color getColor() {
		if (!getGhosting()) {
			return Color.YELLOW;
		}
		return Color.LIGHT_GRAY;
	}

	@Override
	public Shape getShape() {
		if(!getGhosting()) spewFire();
		else isFire = false;
		return new Rectangle2D.Double(this.x, this.y, getSize(), getSize());
	}

	/*
	 * randomly chooses a minimum time to shoot out
	 * puke and stops at random minimum time 
	 */
	public void spewFire() {
		int j = (int) (2000 + Math.random() * 10000);

		if (!isFire) {
			setPaused(false);
			timeSinceLastSpew++;
			if (timeSinceLastSpew > j && !getGhosting() && !checkForCollision()) {
				isFire = true;
				setPaused(true);
				timeSinceLastSpew = 0;
			}
		}

		else{
			setPaused(true);
			timeSpentSpewing++;

			this.world.monstersToAdd
					.add(new Puke(getDirectionOfPlayer(), getCenterPoint(), this.world, this.world.getPlayer(), 1, 1));

			if (timeSpentSpewing > j - 1000) {
				isFire = false;
				setPaused(false);
				timeSpentSpewing = 0;

			}

		}

	}
@Override
public void die() {
	for(int i = 0; i<100; i++){
		this.world.monstersToAdd
		.add(new Puke(new Point2D.Double(0.5-Math.random(),0.5-Math.random()), getCenterPoint(), this.world, this.world.getPlayer(), 5, 0.5));
	}
	super.die();
	
	
	/*
	 * when moveTo() is called, the monster is essentially
	 * reset
	 */
}
	@Override
	public void moveTo(Point2D point) {
		// TODO Auto-generated method stub
		super.moveTo(point);
		this.isFire = false;
		setPaused(false);
		this.timeSpentSpewing = 0;
		this.timeSinceLastSpew = 0;
		exitGhost();
	}



	public void exitGhost() {
		this.setGhosting(false);
		setCollisions(-1);
	}
}
