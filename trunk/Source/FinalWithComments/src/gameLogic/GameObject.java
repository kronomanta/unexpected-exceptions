package gameLogic;

// Abstract class for each game object found in the game. Knows each position of the objects, their sizes and which level part is the given object found in.
public abstract class GameObject {
	protected IBounds bounds;
	protected LevelPart levelPart;

	// Getters and setters
	public IBounds getBounds() {
		return this.bounds;
	}

	protected void setBounds(IBounds bounds) {
		this.bounds = bounds;
	}

	public LevelPart getLevelPart() {
		return this.levelPart;
	}

	protected void setLevelPart(LevelPart levelPart) {
		this.levelPart = levelPart;
	}

	// Constructor
	protected GameObject(LevelPart levelPart) {
		this.levelPart = levelPart;
	}

	public Boolean collidesWith(GameObject other) {		// collision detection function with other game objects having GameObject as parent class
		if (this.levelPart != other.levelPart) {
			return false;
		} else {
			return this.bounds.intersects(other.bounds);
		}
	}
}
