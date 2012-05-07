package main;

import game.ContinuityGame;
import game.renderer.Renderer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;

import javax.swing.Timer;


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

		// Start new game
		this.game = new ContinuityGame();
		
		// Set up and start timer
		this.timer = new Timer(1, new GameCanvasTimerListener(this));
		this.timer.start();
	}
	// Animation core of graphic engine within the canvas
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

		// Plugs into GameCanvas
		GameCanvasTimerListener(GameCanvas gameCanvas) {
			this.gameCanvas = gameCanvas;
		}

		// Calls graphic engine in case of action performed
		public void actionPerformed(ActionEvent arg0) {
			this.gameCanvas.tick();
		}
	}
}
