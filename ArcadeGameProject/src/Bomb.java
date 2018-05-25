import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
/*
 * this class extends the puke class, it is the
 * bomb that is shot out from the BombingTomatoHead 
 * monster 
 */
public class Bomb extends Puke {
	private Color color;
	private double MaxSize;
	private DrugWorld world;
	private double tempSize;
	private Point2D point;
	private Player player;
	
//	Creates a ball that fires towards the player through objects, exploding once reaching
//	a certain size, creating a puke blast in that area that does respond to blocks.

	public Bomb(Point2D direction, Point2D location, DrugWorld dW, Player player) {
		super(direction, location, dW, player,1,1);
		// TODO Auto-generated constructor stub.
		this.color = new Color((int) (50 + Math.random() * 204), (int) (50 + Math.random() * 204), 0);
		this.MaxSize = (Math.random() * 75+40);
		this.world = dW;
		this.point=location;
		this.tempSize=30;
		this.player=player;
		setDirection(2*direction.getX()+direction.getX()*(0.5-Math.random()), 2*direction.getY()+direction.getY()*(0.5-Math.random()));
		setWorth(15);
	}
	
	
	
	@Override
	public void timePassed() {
		// This "monster" moves continuously in the direction of the player from when
		// it was created until it reaches its randomly assigned max size. It will then explode
		// into a puke barage that tends to fire towards the player, but spreads out pretty evenly. 
		move();
		checkForPlayerKill();
		this.tempSize+=.09;
		if (this.MaxSize <= this.tempSize){
			die();
			for(int i=0; i<100;i++){
			this.world.monstersToAdd.add(new Puke(getDirectionOfPlayer(), getCenterPoint(), this.world, this.player,5,1));
			}
		}
	}
	
	@Override
	public Shape getShape(){
		return new Ellipse2D.Double(getLocation().getX(),getLocation().getY(),tempSize,tempSize) ;
		
	}
	
	@Override
	public Color getColor(){
		return this.color;
		
	}
	@Override
	public void move() {
		moveTo(new Point2D.Double(getLocation().getX() + getDirection().getX(),
				getLocation().getY() + getDirection().getY()));
		}
	
	
}


