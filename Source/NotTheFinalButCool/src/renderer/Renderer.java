package renderer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Renderer {
	// Private variables
	private RenderTransform transform;
	private Graphics2D graphics;

	// Getters and setters
	public RenderTransform getTransform() {
		return this.transform;
	}

	public void setTransform(RenderTransform transform) {
		this.transform = transform;

		AffineTransform affine = new AffineTransform();
		affine.scale(transform.getScale(), transform.getScale());
		affine.translate(transform.getTranslateX(), transform.getTranslateY());
		this.graphics.setTransform(affine);
	}

	// Constructors
	public Renderer(Graphics2D graphics) {
		this.graphics = graphics;

		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		this.graphics.setRenderingHints(hints);
	}

	// Public methods
	public void drawImage(float x, float y, Image image) {
		drawImage(new Point(x, y), image);
	}

	public void drawImage(Point target, Image image) {
		drawImage(new Rectangle(target.getX(), target.getY(), image.getWidth(), image.getHeight()), image);
	}

	public void drawImage(Rectangle target, Image image) {
		drawImage(target, new Rectangle(0, 0, image.getWidth(), image.getHeight()), image);
	}

	public void drawImage(Rectangle target, Rectangle source, Image image) {
		BufferedImage awtImage = image.getBufferedImage();

		int sourceX = Math.round(source.getX());
		int sourceY = Math.round(source.getY());

		int targetX = Math.round(target.getX());
		int targetY = Math.round(target.getY());
		int targetWidth = Math.round(target.getWidth());
		int targetHeight = Math.round(target.getHeight());

		java.awt.Rectangle sourceRectangle = new java.awt.Rectangle(sourceX + targetX, sourceY + targetY, image.getWidth(), image.getHeight());
		TexturePaint texture = new TexturePaint(awtImage, sourceRectangle);

		this.graphics.setPaint(texture);
		this.graphics.fillRect(targetX, targetY, targetWidth, targetHeight);
	}

	public void drawRectangle(Rectangle target, Color color) {
		drawRectangle(target.getX(), target.getY(), target.getWidth(), target.getHeight(), color);
	}

	public void drawRectangle(float x, float y, float width, float height, Color color) {

		int targetX = Math.round(x);
		int targetY = Math.round(y);
		int targetWidth = Math.round(width);
		int targetHeight = Math.round(height);

		setColor(color);
		this.graphics.fillRect(targetX, targetY, targetWidth, targetHeight);
	}

	public void drawPolygon(Point[] points, Color color) {
		setColor(color);
		Polygon polygon = new Polygon();
		for (Point point : points) {
			int x = Math.round(point.getX());
			int y = Math.round(point.getY());
			polygon.addPoint(x, y);
		}
		this.graphics.fillPolygon(polygon);
	}

	public void drawText(Point target, String text, int fontSize, Color color) {
		setColor(color);
		Font font = new Font("Impact", Font.PLAIN, fontSize);
		this.graphics.setFont(font);
		this.graphics.drawString(text, target.getX(), target.getY() + fontSize);
	}

	// Helper methods
	private void setColor(Color color) {
		this.graphics.setColor(new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue()));
	}
}
