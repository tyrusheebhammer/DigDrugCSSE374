package combat;

import java.awt.geom.Point2D;

public interface AttackBehavior {
	public void attack(Point2D centerpoint);

	public void reset();
	
	public boolean checkForAttack();
	
	public void updatePlayerDirection(Point2D direction);
}
