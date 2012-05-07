package game;

import game.level.FrameRateCounterComponent;
import game.renderer.RenderTransform;
import game.renderer.Renderer;

public class ContinuityGame {
	// Private fields
	private Boolean firstTick = true;
	private long firstTickTime;
	private long lastTickTime;

	private ContinuityGameState state;
	private MenuScene menuScene;
	private GameScene gameScene;
	private FrameRateCounterComponent fpsCounter;

	// Constructors
	public ContinuityGame() {
		this.state = ContinuityGameState.Menu;
		this.menuScene = new MenuScene();
		this.gameScene = new GameScene();
		this.fpsCounter = new FrameRateCounterComponent();
	}

	// Public methods
	public void tick(Renderer renderer) {
		GameTime gameTime = calculateGameTime();

		// Either call menu or game mode of time passing
		switch (state) {
		case Menu:
			menuTick(gameTime, renderer);
			break;
		case Game:
			gameTick(gameTime, renderer);
			break;
		default:
			// Program shouldn't reach this state.
		}
		
		renderer.setTransform(new RenderTransform());
		this.fpsCounter.update(gameTime);
		this.fpsCounter.draw(gameTime, renderer);
	}
	
	// Private methods
	// calculates elapsed and total time spent in game
	private GameTime calculateGameTime() {
		long currentTime = System.currentTimeMillis();

		// initialize game timer
		if (firstTick) {
			this.firstTickTime = currentTime;
			this.lastTickTime = currentTime;

			firstTick = false;
		}

		// sets up elapsed time and total time then converts from milliseconds to seconds
		float elapsedTime = (currentTime - this.lastTickTime) / 1000.0f;
		float totalTime = (currentTime - this.firstTickTime) / 1000.0f;
		this.lastTickTime = currentTime;
		
		// returns with a GameTime object of elapsed time since last call and total elapsed time
		return new GameTime(elapsedTime, totalTime);
	}

	private void menuTick(GameTime gameTime, Renderer renderer) {
		// updates game time and draws menu
		this.menuScene.update(gameTime);
		this.menuScene.draw(gameTime, renderer);

		// either initialize new game or continue old game
		MenuSceneAction menuAction = this.menuScene.getAction();
		switch (menuAction) {
		case StartNew:
			this.gameScene.initalize(0);
			this.state = ContinuityGameState.Game;
			break;
		case Continue:
			this.gameScene.initalize(Levels.getCurrentLevelIndex());
			this.state = ContinuityGameState.Game;
			break;
		default:
			// Program shouldn't reach this state.
		}
	}
	private void gameTick(GameTime gameTime, Renderer renderer) {
		// updates game time and draws scene
		this.gameScene.update(gameTime);
		this.gameScene.draw(gameTime, renderer);

		// quit current game and return to menu
		GameSceneAction gameAction = this.gameScene.getAction();
		switch (gameAction) {
		case Quit:
			this.state = ContinuityGameState.Menu;
			break;
		default:
			// Program shouldn't reach this state.
		}
	}
}
