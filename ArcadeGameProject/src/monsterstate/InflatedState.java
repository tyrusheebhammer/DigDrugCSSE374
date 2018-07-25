package monsterstate;

import java.awt.Color;
import java.awt.geom.Point2D;

import game.Monster;

public class InflatedState extends AbstractMonsterState {

	private int shrinkFactor;
	private double popRatio;
	public InflatedState(Monster monster, Color color) {
		this.monster = monster;
		this.color = color;
		this.monster.setDirection(0, 0);
		this.shrinkFactor = 300;
		this.popRatio = 2.5;
	}


	@Override
	public void move() {
		//In an attempt to move, the monster shrinks.
		shrink();
	}

	@Override
	public void inflate() {
		this.monster.setSize(this.monster.getSize() + 15);
		Point2D drawPoint = this.monster.getDrawPoint();
		this.monster.setDrawPoint(new Point2D.Double(drawPoint.getX() - 15, drawPoint.getY() - 15));
		if (this.monster.getSize() >= this.monster.getOriginalSize() * this.popRatio ) {
			this.monster.die();
		}
		
	}
	
	@Override
	public void shrink() {
		double size = this.monster.getSize();
		Point2D drawPoint = this.monster.getDrawPoint();
		double x = drawPoint.getX();
		double y = drawPoint.getY();
		
		this.monster.setSize(size - this.monster.getOriginalSize() / this.shrinkFactor);
		this.monster.setDrawPoint(new Point2D.Double(x + ((this.monster.getOriginalSize() / this.shrinkFactor) / 2),
					y + (this.monster.getOriginalSize() / this.shrinkFactor) / 2));
		
		

	}
	
	@Override
	public void checkForChange() {
		if(this.monster.getSize() <= this.monster.getOriginalSize()) {
			this.monster.setSize(this.monster.getOriginalSize());
			changeState("normal");
		}
	}
}
