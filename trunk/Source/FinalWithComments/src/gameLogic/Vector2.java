package gameLogic;

// Read-only class for two dimensional vector.
public class Vector2 {
	private float x;
	private float y;
	
	// Getters
	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

	// Constructor
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
