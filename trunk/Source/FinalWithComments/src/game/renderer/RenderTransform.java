package game.renderer;

public class RenderTransform {						// class for basic geometric transformations
	private float translateX;
	private float translateY;
	private float scale;
	
	public float getTranslateX() {					// returns X
		return this.translateX;
	}
	
	public void setTranslateX(float translateX) {			// sets X
		this.translateX = translateX;
	}
	
	public float getTranslateY() {					// returns Y
		return this.translateY;
	}
	
	public void setTranslateY(float translateY) {			// sets Y
		this.translateY = translateY;
	}
	
	public float getScale() {					// returns scale
		return this.scale;
	}
	
	public void setScale(float scale) {				// sets scale
		this.scale = scale;
	}
	
	public RenderTransform() {					// null transformation (doesn't do anything)
		this(0.0f, 0.0f, 1.0f);
	}
	
	public RenderTransform(float translateX, float translateY, float scale) {	// create given transformation instance
		this.translateX = translateX;
		this.translateY = translateY;
		this.scale = scale;
	}
}
