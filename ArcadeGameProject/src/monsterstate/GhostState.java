package monsterstate;

import java.awt.Color;
import java.awt.geom.Point2D;

import game.Monster;

public class GhostState extends AbstractMonsterState {
	int collisions;

	public GhostState(Monster monster, Color color) {
		this.monster = monster;
		this.color = color;
		this.monster.setDirection(0, 0);
		this.collisions = 0;
	}



	@Override
	public void move() {
		if(monster.checkForCollision()) {
			collisions++;
		}
		Point2D drawPoint = monster.getDrawPoint();
		Point2D direction = monster.getDirectionOfPlayer(); //direction is now that of the player.
		double x = drawPoint.getX();
		double y = drawPoint.getY();
		double dx = direction.getX();
		double dy = direction.getY();
		
		// doing actual movement
		switch (monster.getWorld().isInsideWorld(drawPoint, (int) monster.getSize())) {
		case 'g':
			monster.setDrawPoint(new Point2D.Double(x + dx, y + dy));
			break;
		case 'x':
			monster.setDrawPoint(new Point2D.Double(x - dx * 10, y + dy));
			monster.setDirection(dx*-1, dy);
			this.collisions++;
			break;
		case 'y':
			monster.setDrawPoint(new Point2D.Double(x + dx, y - dy));
			monster.setDirection(dx, dy*-1);
			this.collisions++;
			break;
		default:
			break;
		}
	}


	@Override
	public void inflate() {
		super.inflate();
		collisions = 0;

	}
	
	@Override
	public void checkForChange() {
		if(collisions >= 1000) {
			changeState("normal");
			collisions = 0;
		}
	}

}
