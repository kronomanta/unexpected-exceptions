package engine;

public abstract class Renderer {
	private RenderTransform transform;
	
	public void drawImage(Rectangle target, Image image) {
		drawImage(target, new Rectangle(0, 0, image.getWidth(), image.getHeight()), image);
	}
	public abstract void drawImage(Rectangle target, Rectangle source, Image image);

	public void drawRectangle(Rectangle target, Color color) {
		drawRectangle(target.getX(), target.getY(), target.getWidth(), target.getHeight(), color);
	}
	public abstract void drawRectangle(float x, float y, float width, float height, Color color);
	public abstract void drawPolygon(Point[] points, Color color);
	public abstract void drawText(Point target, String text, int fontSize, Color color);
	
	public void setTransform(RenderTransform transform) {
		this.transform = transform;
	}
	
	public RenderTransform getTransform() {
		return this.transform;
	}
}
