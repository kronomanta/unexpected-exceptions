package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import engine.SwingRenderer;

import game.ContinuityGame;

class GameCanvas extends Canvas {
	private static final long serialVersionUID = 3327996364041119322L;

	private SwingRenderer renderer;
	private ContinuityGame game;

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
	}
}
