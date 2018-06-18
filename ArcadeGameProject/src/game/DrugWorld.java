package game;
import java.awt.Color;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import javafx.geometry.Dimension2D;

/*
 * This class handles the interactions between all of the sprites by
 * controlling the deaths, the reading of the map, and keeps count of 
 * the levels.
 */
public class DrugWorld implements Temporal, Drawable, ActionListener {
	private DrugComponent DC;
	private ArrayList<DirtBlock> blocks = new ArrayList<>();
	private ArrayList<DirtBlock> blocksToKill = new ArrayList<>();
	private ArrayList<Rock> rocks = new ArrayList<>();
	private ArrayList<Rock> rocksToKill = new ArrayList<>();
	public ArrayList<TomatoHead> monstersToAdd = new ArrayList<>();
	public ArrayList<TomatoHead> monsters = new ArrayList<>();
	public ArrayList<TomatoHead> monstersToKill = new ArrayList<>();
	private String[] levels = { "Level1.txt", "Level2.txt", "Level3.txt", "Level4.txt", "Level5.txt", "Level6.txt" };

	private Player player;
	private BonusScoreObject bonus;
	public PlayerWeapon weapon;
	private int currentLevel;
	private static final Color COLOR = Color.BLACK;
	protected static final long UPDATE_INTERVAL_MS = 10;
	private Rectangle2D background;
	private boolean paused, madeBonus;
	private double numRocksKilled = 0;
	private int delay = 0;

	public DrugWorld(DrugComponent DC) {
		this.currentLevel = 1;
		this.DC = DC;
		this.background = new Rectangle2D.Double(0, 0, 972, 810);
		this.paused = false;
		readMap();
		Runnable tickTock = new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						Thread.sleep(UPDATE_INTERVAL_MS);
						if (monsters.size() == 0) {
							Thread.sleep(1000);
							nextLevel(true);

						}
						if (!paused) {

							timePassed();
						}

					}
				} catch (InterruptedException exception) {
					// Stop when interrupted
				}
			}
		};
		new Thread(tickTock).start();
	}

	public boolean getTotalReset() {
		return this.player.getTotalReset();

	}

	public ArrayList<DirtBlock> getDirt() {
		return this.blocks;
	}

	/*
	 * Checks to see if the given point is inside the world, and returns a
	 * character depending on where the point is in the map. if it is out of the
	 * width range, returns x, if out by height, return y, else return g for
	 * good
	 */
	public char isInsideWorld(Point2D point, int size) {
		if (point.getX() >= background.getWidth() - size / 2 - 2 || point.getX() <= 0 + size / 2 + 2)
			return 'x';
		else if (point.getY() >= background.getHeight() - size / 2 - 2 || point.getY() <= 0 + size / 2 + 2)
			return 'y';
		else
			return 'g';
	}

	/*
	 * resets the level depending on if the the reset is because of manual
	 * change, or if the player died
	 */
	public void resetLevel(boolean isDifLevel) {
		if (!isDifLevel) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (TomatoHead m : monsters) {
				m.moveTo(m.getOriginalLocation());
			}

			this.player.moveTo(this.player.getOriginalLocation());
		} else {

			blocks.clear();
			monsters.clear();
			rocks.clear();
			this.removeBonus(bonus);
			this.numRocksKilled = 0;
			this.madeBonus = false;
			readMap();
		}

	}

	/*
	 * goes to the previous level
	 */
	public void previousLevel() {
		if (this.currentLevel == 1) {
			System.out.println("This is the first Level, no previous levels");
			return;
		}
		numRocksKilled = 0;
		this.currentLevel--;
		resetLevel(true);
	}

	/*
	 * goes to the next level, reseting the level
	 */
	public void nextLevel(boolean nextFromWin) {
		if (this.currentLevel == 6) {

			return;
		}
		numRocksKilled = 0;
		this.currentLevel++;
		resetLevel(true);
	}

	/*
	 * gets all of the drawable things and packs them into an arrayList to be
	 * drawn by the drugComponent class
	 * 
	 * THIS CAN PROBABLY BE FIXED WITH STRATEGIES
	 */
	public synchronized List<Drawable> getDrawableParts() {
		ArrayList<Drawable> drawables = new ArrayList<>();
		drawables.addAll(this.blocks);
		drawables.addAll(this.rocks);
		if (this.player != null)
			drawables.add(this.player);
		if (this.weapon != null)
			drawables.add(this.weapon);
		if (this.bonus != null) {
			drawables.add(this.bonus);
		}
		drawables.addAll(this.monsters);
		return drawables;
	}

	/*
	 * find the nearest monster that the player may intersect and be killed by
	 * DELEGATE THIS TO OBSERVER PATTERN**********************
	 */
	public TomatoHead nearestMonster(Point2D point) {
		TomatoHead monster = null;

		try {

			double nearestDistance = Double.MAX_VALUE;
			if (this.monsters.size() != 0) {
				for (TomatoHead m : this.monsters) {

					double distance = point.distanceSq(m.getCenterPoint());
					if (distance < nearestDistance) {
						nearestDistance = distance;
						monster = m;

					}
				}
			}
		} catch (Exception e) {
			System.out.println("Retry");
		}
		return monster;
	}

	/*
	 * find the nearest dirt that the player may end up digging
	 * 
	 * DELEGATE THIS TO AN OBSERVER PATTERN
	 */
	public DirtBlock nearestDirt(Point2D point) {
		DirtBlock nearestBlock = null;

		try {

			double nearestDistance = Double.MAX_VALUE;
			if (this.blocks.size() != 0) {
				for (DirtBlock b : this.blocks) {

					double distance = point.distanceSq(b.getCenterPoint());
					if (distance < nearestDistance) {
						nearestDistance = distance;
						nearestBlock = b;

					}
				}
			}
		} catch (Exception e) {
		}
		return nearestBlock;

	}

	/*
	 * find the nearest rock that either a player or monster may be crushed by
	 * while it's moving or if it is touching and can't move through
	 * 
	 * DELEGATE THIS TO AN OBSERVER PATTERN
	 */
	public Rock nearestRock(Point2D point) {
		Rock nearestRock = null;

		try {

			double nearestDistance = Double.MAX_VALUE;
			if (this.blocks.size() != 0) {
				for (Rock b : this.rocks) {

					double distance = point.distance(b.getCenterPoint());
					if (distance < nearestDistance) {
						nearestDistance = distance;
						nearestRock = b;

					}
				}
			}
			// nearestBlock.setColor(Color.GREEN);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return nearestRock;

	}

	/*
	 * this method reads the map through designated files in the package. the
	 * readMap is dependent on which level the world is currently on and then
	 * packs everything into arrayLists which are then sent to the drugComponent
	 * class to be drawn
	 */
	@SuppressWarnings("boxing")
	private void readMap() {
		String fileName = levels[currentLevel - 1];

		String line = null;

		FileReader testFileReader;
		ArrayList<String> levelLines = new ArrayList<>();
		ArrayList<Integer> individualBlocks = new ArrayList<>();
		try {
			testFileReader = new FileReader(fileName);
			@SuppressWarnings("resource")
			BufferedReader testBufferedReader = new BufferedReader(testFileReader);
			while ((line = testBufferedReader.readLine()) != null) {
				levelLines.add(line);
			}

		} catch (IOException exception) {

			exception.printStackTrace();
			System.out.println("Unable to open " + fileName);
		}

		for (String blockzrz : levelLines) {
			for (int i = 0; i < blockzrz.length(); i++) {
				individualBlocks.add(Integer.parseInt(blockzrz.substring(i, i + 1)));

			}

		}

		/*******/
		try {
			for (int i = 0; i < 30; i++) {
				for (int j = 0; j < 36; j++) {
					switch (individualBlocks.get(i * 36 + j)) {
					case 1:
						this.blocks.add(new DirtBlock(27 * j, 27 * i, this));
						break;
					case 2:
						this.rocks.add(new Rock(27 * j, 27 * i, this));
						break;
					case 3:
						// Only creating a new player if he wasn't already
						// created
						if (this.player == null)
							this.player = new Player(27 * j, 27 * i, this);
						else
							this.player.moveTo(new Point2D.Double(27 * j, 27 * i));
						break;
					case 4:

						this.monsters.add(new TomatoHead(27 * j, 27 * i, this, this.player));

						break;
					case 5:

						this.monsters.add(new PukingTomatoHead(27 * j, 27 * i, this, this.player));

						break;

					case 6:

						this.monsters.add(new BombingTomatoHead(27 * j, 27 * i, this, this.player));

						break;
					case 7:
						this.monsters.add(new UnstableBoss(27 * j, 27 * i, this, this.player));
						break;

					}

				}
			}
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}

	}

	public void togglePaused() {
		this.paused = !paused;
	}

	public DrugComponent getComponent() {
		return this.DC;

	}

	public int getPlayerLives() {
		return this.player.getLives();
	}

	public double getPlayerShieldLeft() {
		return this.player.getShieldLeft();
	}

	public int getPlayerScore() {
		return this.player.getScore();
	}

	public void setPlayerScore(int score) {
		this.player.setPlayerScore(score);
	}

	public void removeBlock(DirtBlock d) {
		this.blocksToKill.add(d);
	}

	public void removeRock(Rock rock) {
		this.rocksToKill.add(rock);

	}

	public void removeMonster(TomatoHead monster) {
		this.monstersToKill.add(monster);

	}

	public void removeBonus(BonusScoreObject bonus) {
		this.bonus = null;
		this.madeBonus = false;
		this.numRocksKilled = 0;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub.
		return COLOR;
	}

	@Override
	public Shape getShape() {
		// TODO Auto-generated method stub.
		return this.background;
	}

	@Override
	public void die() {
		// Can't Die

	}

	/*
	 * this method handles the updating of the sprites in the world, and handles
	 * killing or adding the sprites that are put into the kill or add
	 * arrayLists
	 * 
	 * WE COULD PROBABLY IMPLEMENT SOME STRATEGIES HERE TO CLEAN UP THE CODE,
	 * ALSO ADD OBSERVERS HERE SO WE DON'T NEED TO CONSTANTLY CHECK TO SEE
	 * IF SOME OF THE SPRITES HAVE BEEN REMOVED
	 */
	@Override
	public synchronized void timePassed() {

		try {
			if (this.monsters != null) {
				for (Temporal t : this.monsters) {
					t.timePassed();
				}
			}
			if (this.rocks != null) {
				for (Temporal t : this.rocks) {
					t.timePassed();
				}
			}
		} catch (ConcurrentModificationException e) {
			System.out.println("again");
		}

		if (delay > 0) {
			delay--;
		}
		if (this.player != null)
			this.player.timePassed();
		this.blocks.removeAll(this.blocksToKill);
		this.blocksToKill.clear();

		this.numRocksKilled += rocksToKill.size();

		this.rocks.removeAll(this.rocksToKill);
		this.rocksToKill.clear();
		if (this.numRocksKilled == 2 && this.madeBonus == false) {
			this.bonus = new BonusScoreObject(this.background.getCenterX(), this.background.getCenterY(), this);
			this.madeBonus = true;
		}
		this.monsters.addAll(this.monstersToAdd);
		this.monstersToAdd.clear();
		this.monsters.removeAll(this.monstersToKill);
		this.monstersToKill.clear();

		if (this.bonus != null) {
			bonus.checkForEaten();
		}

	}

	public Player getPlayer() {
		return this.player;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("I was called");
		this.player.move();

	}

	/*
	 * fires the player weapon and adds a ~.5 second delay before the next time
	 * it can be fired
	 * 
	 * THIS SHOULD BE MOVED TO PLAYER CLASS
	 */
	public void fireWeapon() {
		if (delay == 0) {
			PlayerWeapon woosh = new PlayerWeapon(this.player.getCenterPoint(), this.player.Direction, this);
			this.weapon = woosh;
			this.weapon.fire();
			delay += 50;
		}

	}

	public Dimension2D getWorldDimension() {
		return new Dimension2D(this.background.getWidth(), this.background.getHeight());
	}

}
