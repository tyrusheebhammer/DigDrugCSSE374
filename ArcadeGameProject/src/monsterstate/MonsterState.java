package monsterstate;

public interface MonsterState {
	public void timePassed();
	
	public void move();
	
	public void attack();
	
	public void inflate();
	
	public void checkForChange();
	
	public void shrink();
}
