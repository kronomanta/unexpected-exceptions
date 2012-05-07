package game.renderer;

public class Rectangle extends Point {						// class for handling rectangles
	private float width;
	private float height;

	public float getWidth() {						// returns rectangle width
		return this.width;
	}

	public float getHeight() {						// returns rectangle Height
		return this.height;
	}
	
	public Rectangle(float x, float y, float width, float height) {		// creates rectangle from given starting point
		super(x, y);
		this.width = width;
		this.height = height;
	}
}
