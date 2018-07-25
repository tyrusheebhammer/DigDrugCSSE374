package monsterstate;

import java.awt.Color;
import java.awt.geom.Point2D;

import game.Monster;

public class NormalState extends AbstractMonsterState {
	int collisions;

	public NormalState(Monster monster, Color color) {
		this.monster = monster;
		this.color = color;
		this.monster.setDirection(1, 0);
		this.collisions = 0;
	}

	public void collideMove() {
		this.collisions++;
		Point2D direction = this.monster.getDirection();
		this.monster.setDirection(direction.getX() * -1, direction.getY());
	}

	// Probably a good idea to turn movement into a strategy
	@Override
	public void move() {
		if (this.monster.checkForCollision()) {
			collideMove();
			return;
		}
		Point2D drawPoint = this.monster.getDrawPoint();
		double x = drawPoint.getX();
		double y = drawPoint.getY();
		double dx = 1;
		double dy = 0;

		// doing actual movement
		switch (this.monster.getWorld().isInsideWorld(drawPoint, (int) this.monster.getSize())) {
		case 'g':
			this.monster.setDrawPoint(new Point2D.Double(x + dx, y + dy));
			break;
		case 'x':
			this.monster.setDrawPoint(new Point2D.Double(x - dx * 10, y + dy));
			this.monster.setDirection(dx * -1, dy);
			this.collisions++;
			break;
		case 'y':
			this.monster.setDrawPoint(new Point2D.Double(x + dx, y - dy));
			this.monster.setDirection(dx, dy * -1);
			this.collisions++;
			break;
		default:
			break;
		}

	}

	@Override
	public void attack() {
		this.monster.attack();
		if (this.monster.checkForFiring()) {
			changeState("attacking");
		}
	}


	@Override
	public void checkForChange() {
		if (this.collisions >= 5) {
			this.monster.resetAttacks();
			changeState("ghosting");
			this.collisions = 0;
		}
	}
}
