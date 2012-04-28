package main;

import game.ContinuityGame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;

import javax.swing.Timer;

import renderer.Renderer;

class GameCanvas extends Canvas {
	private static final long serialVersionUID = 3327996364041119322L;

	// Private fields
	private Timer timer;
	private ContinuityGame game;

	// Constructors
	GameCanvas() {
		setPreferredSize(new Dimension(800, 600)); // The frame will align.
		addKeyListener(KeyboardState.getInstance());
		setIgnoreRepaint(true);
	}

	// Public methods
	public void start() {
		createBufferStrategy(2);

		this.game = new ContinuityGame();
		
		this.timer = new Timer(1, new GameCanvasTimerListener(this));
		this.timer.start();
	}
	public void tick() {
		BufferStrategy strategy = getBufferStrategy();
		Graphics2D graphics = (Graphics2D) strategy.getDrawGraphics();

		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, 800, 600);

		Renderer renderer = new Renderer(graphics);
			this.game.tick(renderer);
		KeyboardState.getInstance().tick();

		graphics.dispose();
		strategy.show();
		Toolkit.getDefaultToolkit().sync();
	}

	// GameCanvasTimerListener nested class
	private class GameCanvasTimerListener implements ActionListener {
		GameCanvas gameCanvas;

		GameCanvasTimerListener(GameCanvas gameCanvas) {
			this.gameCanvas = gameCanvas;
		}

		public void actionPerformed(ActionEvent arg0) {
			this.gameCanvas.tick();
		}
	}
}
