package main;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import engine.Color;
import engine.Image;
import engine.Point;
import engine.Rectangle;
import engine.RenderTransform;
import engine.Renderer;

public class SwingRenderer extends Renderer {
	private Graphics2D graphics;

	@Override
	public void drawImage(Rectangle target, Rectangle source, Image image) {
		BufferedImage awtImage = ((SwingImage) image).getBufferedImage();

		int sourceX = Math.round(source.getX());
		int sourceY = Math.round(source.getY());
		int sourceWidth = Math.round(source.getWidth());
		int sourceHeight = Math.round(source.getHeight());
		
		int targetX = Math.round(target.getX());
		int targetY = Math.round(target.getY());
		int targetWidth = Math.round(target.getWidth());
		int targetHeight = Math.round(target.getHeight());
		
		java.awt.Rectangle sourceRectangle = new java.awt.Rectangle(sourceX, sourceY, sourceWidth, sourceHeight);
		TexturePaint texture = new TexturePaint(awtImage, sourceRectangle);
		
		this.graphics.setPaint(texture);
		this.graphics.fillRect(targetX, targetY, targetWidth, targetHeight);
	}

	@Override
	public void drawRectangle(float x, float y, float width, float height, Color color) {
		
		int targetX = Math.round(x);
		int targetY = Math.round(y);
		int targetWidth = Math.round(width);
		int targetHeight = Math.round(height);
		
		setColor(color);
		this.graphics.fillRect(targetX, targetY, targetWidth, targetHeight);
	}

	@Override
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

	@Override
	public void drawText(Point target, String text, int fontSize, Color color) {
		setColor(color);
		Font font = new Font("Impact", Font.PLAIN, fontSize);
		this.graphics.setFont(font);
		this.graphics.drawString(text, target.getX(), target.getY() + fontSize);
	}

	public void setGraphics(Graphics2D graphics) {
		this.graphics = graphics;

		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		this.graphics.setRenderingHints(hints);
	}

	private void setColor(Color color) {
		this.graphics.setColor(new java.awt.Color(color.getR(), color.getG(), color.getB()));
	}


	@Override
	public void setTransform(RenderTransform transform) {
		super.setTransform(transform);
		AffineTransform affine = new AffineTransform();
		affine.scale(transform.getScale(), transform.getScale());
		affine.translate(transform.getTranslateX(), transform.getTranslateY());
		this.graphics.setTransform(affine);
	}
}
