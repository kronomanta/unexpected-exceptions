package main;

import game.ContinuityGame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

class GameCanvas extends Canvas {
	private static final long serialVersionUID = 3327996364041119322L;

	private SwingRenderer renderer;
	private ContinuityGame game;

	// private double translateX;
	// private double translateY;
	// private double scale;

	GameCanvas(SwingRenderer renderer, ContinuityGame game) {
		this.renderer = renderer;
		this.game = game;
		
		setIgnoreRepaint(true);
	}

	public void render() {
		BufferStrategy strategy = getBufferStrategy();
		Graphics2D graphics = (Graphics2D) strategy.getDrawGraphics();

		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, 800, 600);

		this.renderer.setGraphics(graphics);
		this.game.tick();

		graphics.dispose();
		strategy.show();
		Toolkit.getDefaultToolkit().sync();

		// AffineTransform tx = new AffineTransform();
		// tx.translate(translateX, translateY);
		// tx.scale(scale, scale);
		// Graphics2D ourGraphics = (Graphics2D) g;
		// ourGraphics.setColor(Color.WHITE);
		// ourGraphics.fillRect(0, 0, getWidth(), getHeight());
		// ourGraphics.setTransform(tx);
		// ourGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		// RenderingHints.VALUE_ANTIALIAS_ON);
		// ourGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
		// RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		// ourGraphics.setColor(Color.BLACK);
		// ourGraphics.drawRect(50, 50, 50, 50);
		// ourGraphics.fillOval(100, 100, 100, 100);
		// ourGraphics.drawString("Test Affine Transform", 50, 30);
	}
}
