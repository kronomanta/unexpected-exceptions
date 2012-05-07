package game.renderer;

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

	public void setTransform(RenderTransform transform) {					// set transformation
		this.transform = transform;

		AffineTransform affine = new AffineTransform();
		affine.scale(transform.getScale(), transform.getScale());			// scale affinity transformation
		affine.translate(transform.getTranslateX(), transform.getTranslateY());		// translate affinity transformation
		this.graphics.setTransform(affine);						// apply affinity transformation
	}

	// Constructors
	public Renderer(Graphics2D graphics) {
		this.graphics = graphics;

		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		this.graphics.setRenderingHints(hints);
	}

	// Public methods
	public void drawImage(float x, float y, Image image) {					// call Point-parametrized drawimage from x and y coordinates
		drawImage(new Point(x, y), image);
	}

	public void drawImage(Point target, Image image) {					// call Rectangle-parametrized drawimage from Point object
		drawImage(new Rectangle(target.getX(), target.getY(), image.getWidth(), image.getHeight()), image);
	}

	public void drawImage(Rectangle target, Image image) {					// call source/target parametrized drawimage from target rectangle
		drawImage(target, new Rectangle(0, 0, image.getWidth(), image.getHeight()), image);
	}

	public void drawImage(Rectangle target, Rectangle source, Image image) {		// draw image from source to target
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

	public void drawRectangle(Rectangle target, Color color) {				// colorize target rectangle area
		drawRectangle(target.getX(), target.getY(), target.getWidth(), target.getHeight(), color);
	}

	public void drawRectangle(float x, float y, float width, float height, Color color) {	// colorize target rectangle area

		int targetX = Math.round(x);
		int targetY = Math.round(y);
		int targetWidth = Math.round(width);
		int targetHeight = Math.round(height);

		setColor(color);
		this.graphics.fillRect(targetX, targetY, targetWidth, targetHeight);
	}

	public void drawPolygon(Point[] points, Color color) {					// draw colored polygon from Point array
		setColor(color);
		Polygon polygon = new Polygon();
		for (Point point : points) {							// create polygon from given points
			int x = Math.round(point.getX());
			int y = Math.round(point.getY());
			polygon.addPoint(x, y);
		}
		this.graphics.fillPolygon(polygon);						// colorize enclosed area
	}

	public void drawText(Point target, String text, int fontSize, Color color) {		// draw text
		setColor(color);								// colorize drawn text
		Font font = new Font("Impact", Font.PLAIN, fontSize);				// set font attributes
		this.graphics.setFont(font);
		this.graphics.drawString(text, target.getX(), target.getY() + fontSize);
	}

	// Helper methods
	private void setColor(Color color) {							// colorize helper method
		this.graphics.setColor(new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue()));
	}
}
