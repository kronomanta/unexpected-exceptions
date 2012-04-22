package gameLogic;

import java.security.InvalidParameterException;

public interface IBounds {
	/**
	 * Visszatér a téglalap bal felső csúcsának X koordinátájával.
	 */
	public float getLeft();

	/**
	 * Visszatér a téglalap bal felső csúcsának Y koordinátájával.
	 */
	public float getTop();

	/**
	 * Visszatér a téglalap szélességével.
	 */
	public float getWidth();

	/**
	 * Visszatér a téglalap magasságával.
	 */
	public float getHeight();

	/**
	 * Visszatér a téglalap jobb alsó csúcsának X koordinátájával.
	 */
	public float getRight();

	/**
	 * Visszatér a téglalap jobb alsó csúcsának Y koordinátájával.
	 */
	public float getBottom();

	/**
	 * Megadja hogy a téglalap metszi-e a megadott téglalapot.
	 * 
	 * @throws InvalidParameterException
	 *             A paraméterben olyan IBounds típus szerep amit az adott
	 *             implementáció nem támogat.
	 */
	public Boolean intersects(IBounds other) throws InvalidParameterException;

	/**
	 * Megadja hogy a téglalap területe tartalmazza-e a megadott pontot.
	 */
	public Boolean contains(float x, float y);

	/**
	 * Kiszámolja az előjeles mélységét két Rectangle metszetének.
	 * 
	 * @throws InvalidParameterException
	 *             A paraméterben olyan IBounds típus szerep amit az adott
	 *             implementáció nem támogat.
	 */
	public Vector2 getIntersectionDepthWith(IBounds other)
			throws InvalidParameterException;
}
