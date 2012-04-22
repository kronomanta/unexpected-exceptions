package gameLogic;

import java.util.ArrayList;

import model.LevelDescriptor;
import model.LevelObjectDescriptor;
import model.LevelObjectType;
import model.LevelPartDescriptor;

public class Level {
	private int width;
	private int height;
	private LevelPart[][] parts;
	private KeyHolder[] keyHolders;
	private Door door;
	private LevelState state;
	private Player player1;
	private Player player2;

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public LevelPart[][] getParts() {
		return this.parts;
	}

	public KeyHolder[] getKeyHolders() {
		return this.keyHolders;
	}

	public Door getDoor() {
		return this.door;
	}

	public LevelState getState() {
		return this.state;
	}

	public Player getPlayer1() {
		return this.player1;
	}

	public Player getPlayer2() {
		return this.player2;
	}

	public Level(LevelDescriptor descriptor) throws Exception {
		this.width = descriptor.getWidth();
		this.height = descriptor.getHeight();
		this.parts = new LevelPart[this.width][this.height];
		
		// Create LevelParts
		LevelPart[] levelPartList = new LevelPart[descriptor.getParts().length];
		for (int i = 0; i < descriptor.getParts().length; i++) {
			LevelPartDescriptor partDescriptor = descriptor.getParts()[i];
			LevelPart levelPart = new LevelPart(partDescriptor, this.parts);
			levelPartList[i] = levelPart;
			this.parts[partDescriptor.getX()][partDescriptor.getY()] = levelPart;
		}
		
		// Now we can update the LevelParts for calculating passabilities.
		for (int i = 0; i < levelPartList.length; i++) {
			levelPartList[i].update();
		}
		
		// Create objects
		ArrayList<KeyHolder> keyHolders = new ArrayList<KeyHolder>();
		for (int i = 0; i < descriptor.getObjects().length; i++) {
			LevelObjectDescriptor objectDescriptor = descriptor.getObjects()[i];
			LevelPart currentLevelPart = levelPartList[objectDescriptor.getLevelPartIndex()];
			
			if (objectDescriptor.getType() == LevelObjectType.Spawn) { // If we found the spawn, we can create our player.
				if (this.player1 == null) {
					this.player1 = new Player(currentLevelPart, objectDescriptor);
				} else if (this.player2 == null) {
					this.player2 = new Player(currentLevelPart, objectDescriptor);
				} else {
					throw new IllegalArgumentException("There can only be one two spawn points in a level.");
				}
			} else if (objectDescriptor.getType() == LevelObjectType.Key) {
				keyHolders.add(new KeyHolder(currentLevelPart, objectDescriptor));
			} else if (objectDescriptor.getType() == LevelObjectType.Door) {
				if (this.door != null) {
					throw new IllegalArgumentException("There can only be one door.");
				}
				this.door = new Door(currentLevelPart, objectDescriptor);
			}
		}
		this.keyHolders = new KeyHolder[keyHolders.size()];
		keyHolders.toArray(this.keyHolders);

		this.state = LevelState.Normal;
		
		if (this.player1 == null || this.player2 == null) {
			throw new Exception("You must place exactly 2 spawn points on every level.");
		}
		if (this.door == null) {
			throw new Exception("You must place a door on every level.");
		}
		if (this.keyHolders.length == 0) {
			throw new Exception("You must place at least 1 key on every level.");
		}
	}

	public void slide(Direction direction) {
		// Find empty slot
		int x = 0;
		int y = 0;
		Boolean isFound = false;
		for (; x < this.parts.length; x++) {
			for (y = 0; y < this.parts[x].length; y++) {
				if (this.parts[x][y] == null) {
					isFound = true;
					break;
				}
			}
			if (isFound) {
				break;
			}
		}
		if (!isFound)
			return;

		// find neighbour in direction
		LevelPart neighbour;
		int neighbourX = x;
		int neighbourY = y;

		if (direction == Direction.Left) {
			neighbourX += 1;
		} else if (direction == Direction.Up) {
			neighbourY += 1;
		} else if (direction == Direction.Right) {
			neighbourX -= 1;
		} else if (direction == Direction.Down) {
			neighbourY -= 1;
		}

		// Find neighbor reference or return if it doesn't exist in the direction.
		if (neighbourX < 0 || neighbourX > this.parts.length - 1)
			return;
		else if (neighbourY < 0 || neighbourY > this.parts[neighbourX].length - 1)
			return;
		else
			neighbour = this.parts[neighbourX][neighbourY];
		
		// reposition and update every LevelPart
		this.parts[neighbourX][neighbourY] = null;
		this.parts[x][y] = neighbour;

		for (x = 0; x < this.parts.length; x++) {
			for (y = 0; y < this.parts[x].length; y++) {
				if (this.parts[x][y] != null) {
					this.parts[x][y].update();	
				}
			}
		}
	}

	public void update() {
		if (this.state == LevelState.Normal) { // Normal state, looking for the player colliding with keys.
			Boolean hasKeySomewhere = false;
			for (int i = 0; i < this.keyHolders.length; i++) {
				if (this.player1.collidesWith(this.keyHolders[i]) || this.player2.collidesWith(this.keyHolders[i])) {
					this.keyHolders[i].setHasKey(false);
				}

				if (this.keyHolders[i].getHasKey()) {
					hasKeySomewhere = true;
				}
			}
			if (!hasKeySomewhere) { // If there is no more keys, go to CanComplete state.
				this.state = LevelState.CanComplete;
			}
		}
		if (this.state == LevelState.CanComplete) { // CanComplete state, looking for the player colliding with door.
			if (this.player1.collidesWith(this.door) || this.player2.collidesWith(this.door)) { // Go to Completed state.
				this.state = LevelState.Completed;
			}
		}
		// If it's Completed, there is nothing to look for.
	}
}
