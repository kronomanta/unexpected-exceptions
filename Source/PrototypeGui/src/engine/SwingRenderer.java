package engine;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.geom.AffineTransform;

import gameLogic.IBounds;
import gameLogic.Vector2;

public class SwingRenderer{
	private Graphics2D graphics;
	
	public void drawImage(IBounds source, Color color) {
		Vector2[] points = new Vector2[]{
								new Vector2(source.getLeft(),source.getTop()),
								new Vector2(source.getRight(),source.getBottom())
							};
		
		drawPolygon(points,color);
	}
	
	public void drawPolygon(Vector2[] points, Color color) {
		Polygon polygon = new Polygon();
		for (Vector2 point : points) {
			int x = Math.round(point.getX());
			int y = Math.round(point.getY());
			polygon.addPoint(x, y);
		}
		
		this.graphics.setColor(color);
		this.graphics.fillPolygon(polygon);
	}

	public void drawText(Vector2 target, String text, int fontSize, Color color) {
		Font font = new Font("Impact", Font.PLAIN, fontSize);
		this.graphics.setFont(font);
		this.graphics.setColor(color);
		this.graphics.drawString(text, target.getX(), target.getY() + fontSize);
	}
	
	public void setGraphics(Graphics2D graphics) {
		this.graphics = graphics;

		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		this.graphics.setRenderingHints(hints);
	}
	
	public void setTransform(RenderTransform transform) {
		AffineTransform affine = new AffineTransform();
		affine.scale(transform.getScale(), transform.getScale());
		affine.translate(transform.getTranslateX(), transform.getTranslateY());
		this.graphics.setTransform(affine);
	}

	public void drawRectangle(float x, float y, float width, float height, Color color) {
		
		int targetX = Math.round(x);
		int targetY = Math.round(y);
		int targetWidth = Math.round(width);
		int targetHeight = Math.round(height);
		
		this.graphics.setColor(color);
		this.graphics.fillRect(targetX, targetY, targetWidth, targetHeight);
	}
		
}
