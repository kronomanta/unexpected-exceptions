package gameLogic;

/**
 * A síkon egy valós méretű és pozíciójű téglalapot reprezentáló objektum. Csak
 * olvasható attribútumai vannak, inicializálás után végleges. Az objektum egy
 * olyan koordinátarendszert feltételez ahol az X tengely jobbra, az Y tengely
 * lefele növekszik, tehát az origó a bal felső sarokban van.
 * 
 * @author <insert name here>
 */
public class RectangleBounds implements IBounds {
	protected float left;
	protected float top;
	protected float width;
	protected float height;

	/**
	 * Visszatér a téglalap bal felső csúcsának X koordinátájával.
	 */
	public float getLeft() {
		return this.left;
	}

	/**
	 * Visszatér a téglalap bal felső csúcsának Y koordinátájával.
	 */
	public float getTop() {
		return this.top;
	}

	/**
	 * Visszatér a téglalap szélességével.
	 */
	public float getWidth() {
		return this.width;
	}

	/**
	 * Visszatér a téglalap magasságával.
	 */
	public float getHeight() {
		return this.height;
	}

	/**
	 * Visszatér a téglalap jobb alsó csúcsának X koordinátájával.
	 */
	public float getRight() {
		return this.left + this.width;
	}

	/**
	 * Visszatér a téglalap jobb alsó csúcsának Y koordinátájával.
	 */
	public float getBottom() {
		return this.top + this.height;
	}
	
	public Vector2 getCenter() {
		return new Vector2(this.left + this.width / 2.0f, this.top + this.height / 2.0f);
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
	public Boolean intersects(IBounds other) {		
		// Kizárásos alapon dönti el.
		if (other.getRight() <= this.getLeft())
			return false;
		else if (other.getBottom() <= this.getTop())
			return false;
		else if (other.getLeft() >= this.getRight())
			return false;
		else if (other.getTop() >= this.getBottom())
			return false;
		else
			return true;
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
	public Boolean contains(float x, float y) {
		return x >= this.getLeft() && x <= this.getRight()
				&& y >= this.getTop() && y <= this.getBottom();
	}

	/**
	 * Kiszámolja az előjeles mélységét két RectangleBounds metszetének.
	 * 
	 * @param other
	 *            A másik téglalap amivel vizsgáljuk a metszetet.
	 * @return Az átfedés mértéke két metsző téglalap között. Negatív mélysége
	 *         is lehet az irányoktól függően. Ha nem metszik egymást null.
	 */
	public Vector2 getIntersectionDepthWith(IBounds other) {
		// Kiszámolja a szélességek és magasságok felét.
		float halfWidth = this.width / 2.0f;
		float halfHeight = this.height / 2.0f;
		float othersHalfWidth = other.getWidth() / 2.0f;
		float othersHalfHeight = other.getHeight() / 2.0f;

		// Kiszámolja a két téglalap középpontjainak koordinátáit.
		float centerX = this.left + halfWidth;
		float centerY = this.top + halfHeight;
		float othersCenterX = other.getLeft() + othersHalfWidth;
		float othersCenterY = other.getTop() + othersHalfHeight;

		// Kiszámolja a középpontok távolságát és azt a minimum távolságot ami
		// esetén már biztos hogy nem metszik egymást a téglalapok.
		float distanceX = centerX - othersCenterX;
		float distanceY = centerY - othersCenterY;
		float minDistanceX = halfWidth + othersHalfWidth;
		float minDistanceY = halfHeight + othersHalfHeight;

		// Ha nincs metszés visszatér null-lal.
		if (Math.abs(distanceX) > minDistanceX
				|| Math.abs(distanceY) > minDistanceY) {
			return null;
		}

		// Kiszámolja és visszatér a metszés mélységével.
		float depthX = distanceX > 0 ? minDistanceX - distanceX : -minDistanceX
				- distanceX;
		float depthY = distanceY > 0 ? minDistanceY - distanceY : -minDistanceY
				- distanceY;
		return new Vector2(depthX, depthY);
	}
}
