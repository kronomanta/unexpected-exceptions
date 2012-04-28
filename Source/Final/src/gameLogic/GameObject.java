package gameLogic;

/**
 * A LevelPart-on belül elhelyezhető játékelemek őszosztálya, tárolja a
 * pozícióját, méretét és hogy melyik LevelPart-ban található.
 * 
 * @author
 */
public abstract class GameObject {
	protected IBounds bounds;
	protected LevelPart levelPart;

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

	protected GameObject(LevelPart levelPart) {
		this.levelPart = levelPart;
	}

	public Boolean collidesWith(GameObject other) {
		if (this.levelPart != other.levelPart) {
			return false;
		} else {
			return this.bounds.intersects(other.bounds);
		}
	}
}
