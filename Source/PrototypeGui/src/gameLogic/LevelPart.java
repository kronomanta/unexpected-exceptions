package gameLogic;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import model.BlockDescriptor;
import model.LevelPartDescriptor;

public class LevelPart {
	public static final float Width = 10.0f;
	public static final float Height = 10.0f;

	private int x;
	private int y;
	private Block[] blocks;
	private LevelPart[][] levelParts;
	private HashMap<Direction, LevelPart> neighbours;
	private HashMap<Direction, Boolean> passabilities;

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public Block[] getBlocks() {
		return this.blocks;
	}

	public Map<Direction, LevelPart> getNeighbours() {
		return Collections.unmodifiableMap(this.neighbours);
	}

	public Map<Direction, Boolean> getPassabilities() {
		return Collections.unmodifiableMap(this.passabilities);
	}

	public LevelPart(LevelPartDescriptor descriptor, LevelPart[][] levelParts) {
		BlockDescriptor[] blockDescriptors = descriptor.getBlocks();
		this.blocks = new Block[blockDescriptors.length];
		for (int i = 0; i < blockDescriptors.length; i++) {
			this.blocks[i] = new Block(blockDescriptors[i], this);
		}

		this.levelParts = levelParts;
		this.neighbours = new HashMap<Direction, LevelPart>();
		this.passabilities = new HashMap<Direction, Boolean>();
	}

	public void update() {
		updatePosition();

		this.neighbours.clear();
		this.neighbours.put(Direction.Left, getNeighbour(this.x - 1, this.y));
		this.neighbours.put(Direction.Up, getNeighbour(this.x, this.y - 1));
		this.neighbours.put(Direction.Right, getNeighbour(this.x + 1, this.y));
		this.neighbours.put(Direction.Down, getNeighbour(this.x, this.y + 1));

		this.passabilities.clear();
		this.passabilities.put(Direction.Left, isPassableTo(Direction.Left));
		this.passabilities.put(Direction.Up, isPassableTo(Direction.Up));
		this.passabilities.put(Direction.Right, isPassableTo(Direction.Right));
		this.passabilities.put(Direction.Down, isPassableTo(Direction.Down));
	}

	private void updatePosition() {
		Boolean isFound = false;
		for (this.x = 0; this.x < this.levelParts.length; this.x++) {
			for (this.y = 0; this.y < this.levelParts[this.x].length; this.y++) {
				if (this.levelParts[this.x][this.y] == this) {
					isFound = true;
					break;
				}
			}
			if (isFound) {
				break;
			}
		}
	}

	private Boolean isInBlock(float x, float y) {
		for (int i = 0; i < this.blocks.length; i++) {
			if (this.blocks[i].getBounds().contains(x, y)) {
				return true;
			}
		}
		return false;
	}

	private Boolean[] getSide(Direction direction) {
		Boolean[] side = new Boolean[10];

		switch (direction) {
		case Left:
			for (int i = 0; i < 10; i++) {
				side[i] = isInBlock(0.0f, i + 0.5f);
			}
			break;
		case Up:
			for (int i = 0; i < 10; i++) {
				side[i] = isInBlock(i + 0.5f, 0.0f);
			}
			break;
		case Right:
			for (int i = 0; i < 10; i++) {
				side[i] = isInBlock(10.0f, i + 0.5f);
			}
			break;
		case Down:
			for (int i = 0; i < 10; i++) {
				side[i] = isInBlock(i + 0.5f, 10.0f);
			}
			break;
		default:
			throw new IllegalArgumentException("Invalid direction");
		}

		return side;
	}

	private Boolean isPassableTo(Direction direction) {
		LevelPart neighbor = this.neighbours.get(direction);
		if (neighbor == null)
			return false;

		Direction oppositeDirection = DirectionHelper.getOpposite(direction);

		Boolean[] thisSide = this.getSide(direction);
		Boolean[] otherSide = neighbor.getSide(oppositeDirection);
		for (int i = 0; i < thisSide.length; i++) {
			if (thisSide[i] != otherSide[i]) {
				return false;
			}
		}
		return true;
	}

	private LevelPart getNeighbour(int x, int y) {
		if (x < 0 || x > this.levelParts.length - 1)
			return null;
		else if (y < 0 || y > this.levelParts[x].length - 1)
			return null;
		else
			return this.levelParts[x][y];
	}
}
