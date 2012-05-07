package gameLogic;

// This class represents a read-only rectagle for collision handling. The given coordinate system the class is working in has the X axis increase rightwards
// and the Y axis increase downwards - thus the pole is in the upper left corner.
public class RectangleBounds implements IBounds {
	protected float left;
	protected float top;
	protected float width;
	protected float height;

	// returns with the X coordinate of the upper left corner of given rectangle
	public float getLeft() {
		return this.left;
	}

	// returns with the Y coordinate of the upper left corner of given rectangle
	public float getTop() {
		return this.top;
	}

	// returns with the width of given rectangle
	public float getWidth() {
		return this.width;
	}

	// returns with the height of given rectangle
	public float getHeight() {
		return this.height;
	}

	// returns with the X coordinate of the lower right corner of given rectangle
	public float getRight() {
		return this.left + this.width;
	}

	// returns with the Y coordinate of the lower right corner of given rectangle
	public float getBottom() {
		return this.top + this.height;
	}
	
	// returns with the center of given rectangle
	public Vector2 getCenter() {
		return new Vector2(this.left + this.width / 2.0f, this.top + this.height / 2.0f);
	}

	// Constructor
	public RectangleBounds(float left, float top, float width, float height) {
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
	}

	// returns true if this rectangle intersects with the given rectangle
	public Boolean intersects(IBounds other) {		
		// exclusionary decision
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

	// returns true if this rectangle contains the given (X,Y) point
	public Boolean contains(float x, float y) {
		return x >= this.getLeft() && x <= this.getRight()
				&& y >= this.getTop() && y <= this.getBottom();
	}

	// Calculates the depth of given intersection between this rectangle and the other. If they intersect the return value can either be a positive
	// or a negative value, depending on the directions. Returns null if there was no intersection.
	public Vector2 getIntersectionDepthWith(IBounds other) {
		// Calculates the halves of the widths and heights.
		float halfWidth = this.width / 2.0f;
		float halfHeight = this.height / 2.0f;
		float othersHalfWidth = other.getWidth() / 2.0f;
		float othersHalfHeight = other.getHeight() / 2.0f;

		// Calculates the distance between the centers of the two rectangles.
		float centerX = this.left + halfWidth;
		float centerY = this.top + halfHeight;
		float othersCenterX = other.getLeft() + othersHalfWidth;
		float othersCenterY = other.getTop() + othersHalfHeight;

		// Calculates the minimum distance between the centers when the rectangles are no longer intersecting each other.
		float distanceX = centerX - othersCenterX;
		float distanceY = centerY - othersCenterY;
		float minDistanceX = halfWidth + othersHalfWidth;
		float minDistanceY = halfHeight + othersHalfHeight;

		// If no intersection, returns with null.
		if (Math.abs(distanceX) > minDistanceX
				|| Math.abs(distanceY) > minDistanceY) {
			return null;
		}

		// Calculates intersection and returns with it's depth.
		float depthX = distanceX > 0 ? minDistanceX - distanceX : -minDistanceX
				- distanceX;
		float depthY = distanceY > 0 ? minDistanceY - distanceY : -minDistanceY
				- distanceY;
		return new Vector2(depthX, depthY);
	}
}
