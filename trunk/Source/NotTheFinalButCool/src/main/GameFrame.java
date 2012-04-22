package main;

import engine.IViewport;
import game.ContinuityGame;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class GameFrame extends JFrame implements IViewport {
	private static final long serialVersionUID = 3810231706648299200L;

	private Timer timer;

	public GameFrame() {
		super("Continuity");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		SwingRenderer renderer = new SwingRenderer();
		SwingResourceProvider resourceProvider = new SwingResourceProvider();
		SwingKeyboardState keyboardState = new SwingKeyboardState();
		ContinuityGame game = new ContinuityGame(renderer, resourceProvider, keyboardState, this);

		GameCanvas gameCanvas = new GameCanvas(renderer, game);
		gameCanvas.setPreferredSize(new Dimension(this.getViewportWidth(), this.getViewportHeight()));
		gameCanvas.addKeyListener(keyboardState);

		add(gameCanvas);
		setResizable(false);
		pack();
		setVisible(true);
		
		addKeyListener(keyboardState);

		gameCanvas.createBufferStrategy(2);

		this.timer = new Timer(1, new Chrono(gameCanvas));
		this.timer.start();
	}

	private class Chrono implements ActionListener {
		GameCanvas gameCanvas;

		Chrono(GameCanvas gameCanvas) {
			this.gameCanvas = gameCanvas;
		}

		public void actionPerformed(ActionEvent arg0) {
			gameCanvas.render();
		}
	}

	@Override
	public int getViewportWidth() {
		return 800;
	}

	@Override
	public int getViewportHeight() {
		return 600;
	}
}
