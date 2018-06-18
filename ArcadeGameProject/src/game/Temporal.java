package game;
/*
 * This interface is used for objects that 
 * can die or have a way to pass the time
 */
public interface Temporal {
	void die();
	
	void timePassed();
}
