package observerpattern;

import java.awt.geom.Point2D;

public interface PlayerObserver extends Observer{
	public void updatePlayerLocation(Point2D point);
}
