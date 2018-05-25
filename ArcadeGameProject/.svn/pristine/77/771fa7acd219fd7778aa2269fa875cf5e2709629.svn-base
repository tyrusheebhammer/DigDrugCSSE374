import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/*
 * this class is the boss level for the 
 * Boss. The boss is the final level of the game
 * and awards 1500 points upon his death
 * the boss shoots out fire and bombs, so essentially it 
 * is a more powerful puker and bomber. The 
 * boss shoots projectiles more often
 */
public class UnstableBoss extends TomatoHead {

	private boolean isFire;
	private int timeSinceLastSpew;
	private int timeSpentSpewing;
	private int timeSinceLastBomb;
	private Player player;

	public UnstableBoss(double x2, double y2, DrugWorld dW, Player player) {
		super(x2, y2, dW, player);
		this.player = player;
		setSize(100);
		setWorth(1500);
		setOriginalSize(100);
		setPopRatio(7);
	}

	public void spewFire() {
		int j = (int) (2000 + Math.random() * 1000);

		if (!isFire) {
			setPaused(false);
			timeSinceLastSpew++;
			if (timeSinceLastSpew > j) {
				isFire = true;
				setPaused(true);
				timeSinceLastSpew = 0;
			}
		}
		
		else {
			setPaused(true);
			timeSpentSpewing++;

			this.world.monstersToAdd.add(
					new Puke(getDirectionOfPlayer(), new Point2D.Double(centerPoint.getX()+23, centerPoint.getY()+23), this.world, this.world.getPlayer(), 2, 0.8));

			if (timeSpentSpewing > j - 1000) {
				isFire = false;
				setPaused(false);
				timeSpentSpewing = 0;

			}

		}
	}
	
	public void shrink(){
		if (this.size >= getOriginalSize()) {
			this.size -= getOriginalSize() / 1200.0;
			this.x += (getOriginalSize() / 1200.0) / 2;
			this.y += (getOriginalSize() / 1200.0) / 2;
		}
	}

	public void spewRock() {
		int j = (int) (500 + Math.random() * 1000);

		timeSinceLastBomb++;
		if (timeSinceLastBomb > j ) {
			timeSinceLastBomb = 0;
			this.world.monstersToAdd.add(new Bomb(getDirectionOfPlayer(), getCenterPoint(), this.world, this.player));

		}
	}
	@Override
	public void timePassed(){
		moveTo(new Point2D.Double(centerPoint.getX()+getDirectionOfPlayer().getX()/5,centerPoint.getY()+ getDirectionOfPlayer().getY()/5));
		checkForPlayerKill();
		spewRock();
		spewFire();
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
