package factory;

import java.awt.geom.Point2D;

import game.BombingTomatoHead;
import game.DrugWorld;
import game.GameObject;
import game.NormalTomatoHead;
import game.PukingTomatoHead;
import game.UnstableBoss;

public class MonsterObjectFactory implements GameObjectFactory {

	public MonsterObjectFactory() {
		System.out.println("object factory created");
	}
	@Override
	public GameObject createObject(int objectName, Point2D position, DrugWorld world) {
		// TODO Auto-generated method stub.
		System.out.println(objectName);
		System.out.println(position);
		switch (objectName) {
		case 4:
			return new NormalTomatoHead(position.getX(), position.getY(), world);
		case 5:
			return new PukingTomatoHead(position.getX(),position.getY(),world);
		case 6:
			return new BombingTomatoHead(position.getX(),position.getY(),world);
		case 7:
			return new UnstableBoss(position.getX(),position.getY(),world);
		default:
			break;
		}
		return null;
	}

}
