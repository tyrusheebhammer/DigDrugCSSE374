package observerpattern;

import java.awt.geom.Point2D;

public interface Observable {
	public void registerObserver(Observer o);
	public void removeObserver(Observer o);
	public void notifyObservers();
	void notifyObservers(Point2D playerLocation);
}
