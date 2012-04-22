package gameLogic;


import main.SkeletonHelper;


/**
 * A síkon egy valós méretű és pozíciójű téglalapot reprezentáló objektum. Csak
 * olvasható attribútumai vannak, inicializálás után végleges. Az objektum egy
 * olyan koordinátarendszert feltételez ahol az X tengely jobbra, az Y tengely
 * lefele növekszik, tehát az origó a bal felső sarokban van.
 * 
 * @author <insert name here>
 */
public class RectangleBounds implements IBounds {
	private float left;
	private float top;
	private float width;
	private float height;

	/**
	 * Visszatér a téglalap bal felső csúcsának X koordinátájával.
	 */
	@Override
	public float getLeft() {
		return this.left;
	}

	/**
	 * Visszatér a téglalap bal felső csúcsának Y koordinátájával.
	 */
	@Override
	public float getTop() {
		return this.top;
	}

	/**
	 * Visszatér a téglalap szélességével.
	 */
	@Override
	public float getWidth() {
		return this.width;
	}

	/**
	 * Visszatér a téglalap magasságával.
	 */
	@Override
	public float getHeight() {
		return this.height;
	}

	/**
	 * Visszatér a téglalap jobb alsó csúcsának X koordinátájával.
	 */
	@Override
	public float getRight() {
		return this.left + this.width;
	}

	/**
	 * Visszatér a téglalap jobb alsó csúcsának Y koordinátájával.
	 */
	@Override
	public float getBottom() {
		return this.top + this.height;
	}

	/**
	 * Inicializál egy új RectangleBounds példányt.
	 * 
	 * @param left
	 *            A téglalap bal felső csúcsának X koordinátája.
	 * @param top
	 *            A téglalap bal felső csúcsának Y koordinátája.
	 * @param width
	 *            A téglalap szélessége.
	 * @param height
	 *            A téglalap magassága.
	 */
	public RectangleBounds(float left, float top, float width, float height) {
		SkeletonHelper.enterMethod();
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
	}

	/**
	 * Megadja hogy a téglalap metszi-e a megadott téglalapot.
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
	 * Megadja hogy a téglalap területe tartalmazza-e a megadott pontot.
	 * 
	 * @param x
	 *            A vizsgált pont X koordinátája.
	 * @param y
	 *            A vizsgált pont Y koordinátája.
	 * @return Igaz ha a pont benne van a téglalapban, egyébként hamis.
	 */
	@Override
	public Boolean contains(float x, float y) {
		SkeletonHelper.enterMethod();
		return false;
	}

	/**
	 * Kiszámolja az előjeles mélységét két RectangleBounds metszetének.
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
