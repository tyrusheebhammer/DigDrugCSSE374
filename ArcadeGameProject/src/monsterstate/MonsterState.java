package monsterstate;

import java.awt.Color;

public interface MonsterState {
	public void timePassed();
	
	public void move();
	
	public Color getColor();
	
	public void setColor(Color color);
	
	public void attack();
	
	public void inflate();
}
