package renderer;

public class Rectangle extends Point {
	private float width;
	private float height;

	public float getWidth() {
		return this.width;
	}

	public float getHeight() {
		return this.height;
	}
	
	public Rectangle(float x, float y, float width, float height) {
		super(x, y);
		this.width = width;
		this.height = height;
	}
}
