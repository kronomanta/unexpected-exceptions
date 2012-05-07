package main;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 3810231706648299200L;

	public GameFrame() {
		// Set up main window (GameFrame)
		super("Continuity");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create an instance of GameCanvas
		GameCanvas gameCanvas = new GameCanvas();

		// Set up GameCanvas to fit main window (GameFrame)
		add(gameCanvas);
		setResizable(false);
		pack();
		setVisible(true);
		
		// Start game and listen for keyboard command events
		gameCanvas.start();
		
		addKeyListener(KeyboardState.getInstance());
	}
}
