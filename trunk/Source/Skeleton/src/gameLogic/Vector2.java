package gameLogic;

import main.SkeletonHelper;

/**
 * Egy két dimenziós valós vektort reprezentáló objektum. Létrehozás után csak
 * olvasni lehet, változtatni nem.
 * 
 * @author
 */
public class Vector2 {
	private float x;
	private float y;

	/**
	 * Visszatér a vektor X koordinátájával.
	 */
	public float getX() {
		return this.x;
	}

	/**
	 * Visszatér a vektor Y koordinátájával.
	 */
	public float getY() {
		return this.y;
	}

	/**
	 * Inicializál egy új Vector2 példányt.
	 * 
	 * @param x
	 *            A vektor X koordinátája.
	 * @param y
	 *            A vektro Y koordinátája.
	 */
	public Vector2(float x, float y) {
		SkeletonHelper.enterMethod();
		this.x = x;
		this.y = y;
	}
}
