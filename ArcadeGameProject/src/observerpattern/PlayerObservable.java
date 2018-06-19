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
		//System.out.println(o.toString() + " subscribed");
		this.observers.add((PlayerObserver) o);
		this.observers.get(this.observers.size() - 1).updatePlayerLocation(playerLocation);
		// TODO Auto-generated method stub.

	}

	@Override
	public void removeObserver(Observer o) {
		// TODO Auto-generated method stub.
		//System.out.println(o.toString() + " removed");
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
