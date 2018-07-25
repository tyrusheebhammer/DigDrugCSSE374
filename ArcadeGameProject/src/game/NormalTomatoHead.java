package game;

import java.awt.Color;
import java.awt.geom.Point2D;

public class NormalTomatoHead extends Monster {

	public NormalTomatoHead(double x, double y, DrugWorld dW) {
		
		setCollisions(0);
		setWorld(dW);
		setDirection(1,0);

		Color[] colors = {Color.GRAY, Color.MAGENTA, Color.MAGENTA, Color.RED};
		initializeStates(colors);
		

		setWorth(100);
		setPopRatio(2.5);
		setSize(24);

		setOriginalSize(getSize());
		setDrawPoint(new Point2D.Double(x, y));
		setOriginalLocation(getDrawPoint());
		
		setName("TomatoHead ");
	}

}
