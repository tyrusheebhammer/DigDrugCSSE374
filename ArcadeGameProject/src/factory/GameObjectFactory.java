package factory;

import java.awt.geom.Point2D;

import game.DrugWorld;
import game.GameObject;

public interface GameObjectFactory {
	public GameObject createObject(int objectName, Point2D position, DrugWorld world);
}
