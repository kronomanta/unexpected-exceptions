package game.renderer;

public class RenderTransform {
	private float translateX;
	private float translateY;
	private float scale;
	
	public float getTranslateX() {
		return this.translateX;
	}
	
	public void setTranslateX(float translateX) {
		this.translateX = translateX;
	}
	
	public float getTranslateY() {
		return this.translateY;
	}
	
	public void setTranslateY(float translateY) {
		this.translateY = translateY;
	}
	
	public float getScale() {
		return this.scale;
	}
	
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public RenderTransform() {
		this(0.0f, 0.0f, 1.0f);
	}
	
	public RenderTransform(float translateX, float translateY, float scale) {
		this.translateX = translateX;
		this.translateY = translateY;
		this.scale = scale;
	}
}
