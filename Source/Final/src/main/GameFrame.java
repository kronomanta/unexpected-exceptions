package main;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 3810231706648299200L;

	public GameFrame() {
		super("Continuity");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GameCanvas gameCanvas = new GameCanvas();

		add(gameCanvas);
		setResizable(false);
		pack();
		setVisible(true);
		
		gameCanvas.start();
		
		addKeyListener(KeyboardState.getInstance());
	}
}
