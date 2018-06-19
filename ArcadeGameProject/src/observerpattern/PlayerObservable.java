package observerpattern;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class PlayerObservable implements Observable {

	ArrayList<PlayerObserver> observers;
	private Point2D playerLocation;

	public PlayerObservable() {
		this.observers = new ArrayList<>();
	}

	@Override
	public void registerObserver(Observer o) {
		this.observers.add((PlayerObserver) o);
		// TODO Auto-generated method stub.

	}

	@Override
	public void removeObserver(Observer o) {
		// TODO Auto-generated method stub.
		this.observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		for (PlayerObserver o : this.observers) {
			o.updatePlayerLocation(this.playerLocation);
		}

	}

	public void setPlayerLocation(Point2D playerLocation) {
		if (!playerLocation.equals(this.playerLocation)) {
			this.playerLocation = playerLocation;
			notifyObservers();
		}
	}

}
