package main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import engine.SwingKeyboardState;
import engine.SwingRenderer;
import game.Constants;
import game.ContinuityGame;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 3810231706648299200L;

	private Timer timer;

	public GameFrame() {
		super("Continuity");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		SwingRenderer renderer = new SwingRenderer();
		SwingKeyboardState keyboardState = new SwingKeyboardState();
		ContinuityGame game = new ContinuityGame(renderer, keyboardState, this);

		GameCanvas gameCanvas = new GameCanvas(renderer, game);
		gameCanvas.setPreferredSize(new Dimension(Constants.ViewportWidth, Constants.ViewportHeight));
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
}
