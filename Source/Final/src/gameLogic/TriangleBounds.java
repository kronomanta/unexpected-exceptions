package gameLogic;

/**
 * A síkon egy valós méretű és pozíciójú derékszögű háromszöget reprezentáló
 * objektum. Csak olvasható attribútumai vannak, inicializálás után végleges. Az
 * objektum egy olyan koordinátarendszert feltételez ahol az X tengely jobbra,
 * az Y tengely lefele növekszik, tehát az origó a bal felső sarokban van.
 * 
 * @author <insert name here>
 */
public class TriangleBounds extends RectangleBounds {
	private TriangleType type;

	/**
	 * Visszatér a háromszög irányával. A kitöltött rész az int szerint: 0 =
	 * bal-felülre mutat 1 = jobb-felülre mutat 2 = bal-alulra mutat 3 =
	 * jobb-alulra mutat
	 */
	public TriangleType getType() {
		return this.type;
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
			TriangleType type) {
		super(left, top, width, height);
		this.type = type;
	}

	/**
	 * Megadja hogy a háromszög metszi-e a megadott téglalapot.
	 * 
	 * @param other
	 *            A vizsgált másik téglalap.
	 * @return Igaz ha metszi, egyébként hamis.
	 */
	public Boolean intersects(IBounds other) {
		Vector2 depth = getIntersectionDepthWith(other);
		return depth == null ? false : true; 
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
	public Boolean contains(float x, float y) {
		if (super.contains(x, y)) {
			float py;
			if (this.type == TriangleType.BottomLeft) {
				py = this.top + this.getHeight() * (x - this.left) / this.width;
			} else if(this.type == TriangleType.BottomRight) {
				py = this.getBottom() - this.getHeight() * (x - this.left) / this.width;
			} else {
				return false;
			}
			return py <= y;
		} else {
			return false;
		}
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
	public Vector2 getIntersectionDepthWith(IBounds other) {
		if (other.getBottom() <= this.getBottom()) {
			Vector2 depth;
			if (this.type == TriangleType.BottomLeft && other.getLeft() > this.getLeft()) {
				float px = this.left + this.getWidth() * (other.getBottom() - this.top) / this.height;
				
				if (px < other.getLeft())
					return null;
				
				float py = this.top + this.getHeight() * (other.getLeft() - this.left) / this.width;
				depth = new Vector2(other.getLeft() - px, other.getBottom() - py);
			} else if(this.type == TriangleType.BottomRight && other.getRight() < this.getRight()) {
				float verticalRatio = (other.getBottom() - this.top) / this.height;
				float horizontalRatio = (other.getRight() - this.left) / this.width;
				float px = this.getRight() - this.getWidth() * verticalRatio;
				
				if (px > other.getRight())
					return null;

				float py = this.getBottom() - this.getHeight() * horizontalRatio;
				depth = new Vector2(other.getRight() - px, other.getBottom() - py);
			} else {
				return super.getIntersectionDepthWith(other);
			}
			
			return depth;	
		} else {
			return super.getIntersectionDepthWith(other);
		}
	}
}