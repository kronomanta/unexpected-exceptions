package gameLogic;

// This class represents a read-only orthogonal triangle for collision handling. The given coordinate system the class is working in has the X axis increase rightwards
// and the Y axis increase downwards - thus the pole is in the upper left corner.
public class TriangleBounds extends RectangleBounds {
	private TriangleType type;

	// Returns with the type of given triangle. Can be left ramped or right ramped.
	public TriangleType getType() {
		return this.type;
	}

	// Constructor
	public TriangleBounds(float left, float top, float width, float height,
			TriangleType type) {
		super(left, top, width, height);
		this.type = type;
	}

	// returns if this triangle intersects with the given rectangle
	public Boolean intersects(IBounds other) {
		Vector2 depth = getIntersectionDepthWith(other);
		return depth == null ? false : true; 
	}

	// returns true if this triangle contains the given (X,Y) point
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

	// Calculates the depth of given intersection between a triangle and a rectangle. If they intersect the return value can either be a positive
	// or a negative value, depending on the directions. Returns null if there was no intersection.
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
