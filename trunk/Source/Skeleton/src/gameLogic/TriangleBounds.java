package gameLogic;

import main.SkeletonHelper;

/**
 * A síkon egy valós méretű és pozíciójú derékszögű háromszöget reprezentáló
 * objektum. Csak olvasható attribútumai vannak, inicializálás után végleges. Az
 * objektum egy olyan koordinátarendszert feltételez ahol az X tengely jobbra,
 * az Y tengely lefele növekszik, tehát az origó a bal felső sarokban van.
 * 
 * @author <insert name here>
 */
public class TriangleBounds implements IBounds {
	private float left;
	private float top;
	private float width;
	private float height;
	private int direction;

	/**
	 * Visszatér a háromszög bal felső csúcsának X koordinátájával.
	 */
	@Override
	public float getLeft() {
		return this.left;
	}

	/**
	 * Visszatér a háromszög bal felső csúcsának Y koordinátájával.
	 */
	@Override
	public float getTop() {
		return this.top;
	}

	/**
	 * Visszatér a háromszög szélességével.
	 */
	@Override
	public float getWidth() {
		return this.width;
	}

	/**
	 * Visszatér a háromszög magasságával.
	 */
	@Override
	public float getHeight() {
		return this.height;
	}

	/**
	 * Visszatér a háromszög jobb alsó csúcsának X koordinátájával.
	 */
	@Override
	public float getRight() {
		return this.left + this.width;
	}

	/**
	 * Visszatér a háromszög jobb alsó csúcsának Y koordinátájával.
	 */
	@Override
	public float getBottom() {
		return this.top + this.height;
	}

	/**
	 * Visszatér a háromszög irányával. A kitöltött rész az int szerint: 0 =
	 * bal-felülre mutat 1 = jobb-felülre mutat 2 = bal-alulra mutat 3 =
	 * jobb-alulra mutat
	 */
	public int getDirection() {
		return this.direction;
	}

	/**
	 * Inicializál egy új TriangleBounds példányt.
	 * 
	 * @param left
	 *            A háromszög bal felső csúcsának X koordinátája.
	 * @param top
	 *            A háromszög bal felső csúcsának Y koordinátája.
	 * @param width
	 *            A háromszög szélessége.
	 * @param height
	 *            A háromszög magassága.
	 */
	public TriangleBounds(float left, float top, float width, float height,
			int direction) {
		SkeletonHelper.enterMethod();
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
		this.direction = direction;
	}

	/**
	 * Megadja hogy a háromszög metszi-e a megadott téglalapot.
	 * 
	 * @param other
	 *            A vizsgált másik téglalap.
	 * @return Igaz ha metszi, egyébként hamis.
	 */
	@Override
	public Boolean intersects(IBounds other) {
		SkeletonHelper.enterMethod();
		return false;
	}

	/**
	 * Megadja hogy a háromszög területe tartalmazza-e a megadott pontot.
	 * 
	 * @param x
	 *            A vizsgált pont X koordinátája.
	 * @param y
	 *            A vizsgált pont Y koordinátája.
	 * @return Igaz ha a pont benne van a háromszögben, egyébként hamis.
	 */
	@Override
	public Boolean contains(float x, float y) {
		SkeletonHelper.enterMethod();
		return false;
	}

	/**
	 * Kiszámolja az előjeles mélységét egy TriangleBounds és egy
	 * RectangleBounds metszetének.
	 * 
	 * @param other
	 *            A másik téglalap amivel vizsgáljuk a metszetet.
	 * @return Az átfedés mértéke két metsző téglalap között. Negatív mélysége
	 *         is lehet az irányoktól függően. Ha nem metszik egymást null.
	 */
	@Override
	public Vector2 getIntersectionDepthWith(IBounds other) {
		SkeletonHelper.enterMethod();
		return new Vector2(0.0f, 0.0f);
	}
}