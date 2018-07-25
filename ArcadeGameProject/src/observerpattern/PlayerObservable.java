package observerpattern;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class PlayerObservable implements Observable {

	ArrayList<PlayerObserver> observers;
	private Point2D playerLocation;

	public PlayerObservable() {
		this.observers = new ArrayList<>();
		this.playerLocation = new Point2D.Double(500,500);
	}

	@Override
	public void registerObserver(Observer o) {
		//System.out.println(o.toString() + " subscribed");
		this.observers.add((PlayerObserver) o);
		this.observers.get(this.observers.size() - 1).updatePlayerLocation(this.playerLocation);
		// TODO Auto-generated method stub.

	}

	@Override
	public void removeObserver(Observer o) {
		// TODO Auto-generated method stub.
		//System.out.println(o.toString() + " removed");
		this.observers.remove(o);
	}

	@Override
	public void notifyObservers(Point2D playerLocation1) {
		for (PlayerObserver o : this.observers) {
			o.updatePlayerLocation(playerLocation1);
		}

	}

	public void setPlayerLocation(Point2D playerLocation1) {
		if (!playerLocation1.equals(this.playerLocation)) {
			
		}
		this.playerLocation = playerLocation1;
		notifyObservers(playerLocation1);
		
	}

	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub.
		
	}

}
