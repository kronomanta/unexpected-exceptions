package renderer;

public class Point {
	// Private fields
	private float x;
	private float y;
	
	// Getters and setters
	public float getX() {
		return this.x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return this.y;
	}
	public void setY(float y) {
		this.y = y;
	}
	
	// Constructors
	public Point() {
	}
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
}