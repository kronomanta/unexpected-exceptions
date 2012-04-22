package gameLogic;

import main.SkeletonHelper;

/**
 * A LevelPart-on belül elhelyezhető játékelemek őszosztálya, tárolja a
 * pozícióját, méretét és hogy melyik LevelPart-ban található.
 * 
 * @author
 */
public abstract class GameObject {
	private IBounds bounds;
	private LevelPart levelPart;

	public IBounds getBounds() {
		SkeletonHelper.enterMethod();
		return this.bounds;
	}

	protected void setBounds(IBounds bounds) {
		SkeletonHelper.enterMethod();
		this.bounds = bounds;
	}

	public LevelPart getLevelPart() {
		return this.levelPart;
	}

	protected void setLevelPart(LevelPart levelPart) {
		SkeletonHelper.enterMethod();
		this.levelPart = levelPart;
	}

	protected GameObject(LevelPart levelPart) {
		this.levelPart = levelPart;
	}

	public Boolean collidesWith(GameObject other) {
		SkeletonHelper.enterMethod();
		SkeletonHelper.comment("Ha ugyan azon LevelPart-on tartozkodik mindket objektum");
		SkeletonHelper.comment("akkor megvizsgalja hogy metszik-e egymast.");
		return this.bounds.intersects(other.bounds);
	}
}
