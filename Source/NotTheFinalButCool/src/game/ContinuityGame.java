package game;

import game.level.FrameRateCounterComponent;
import renderer.RenderTransform;
import renderer.Renderer;

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

		switch (state) {
		case Menu:
			menuTick(gameTime, renderer);
			break;
		case Game:
			gameTick(gameTime, renderer);
			break;
		default:
			// We should throw excetion here.
		}
		
		renderer.setTransform(new RenderTransform());
		this.fpsCounter.update(gameTime);
		this.fpsCounter.draw(gameTime, renderer);
	}
	
	// Private methods
	private GameTime calculateGameTime() {
		long currentTime = System.currentTimeMillis();

		if (firstTick) {
			this.firstTickTime = currentTime;
			this.lastTickTime = currentTime;

			firstTick = false;
		}

		float elapsedTime = (currentTime - this.lastTickTime) / 1000.0f;
		float totalTime = (currentTime - this.firstTickTime) / 1000.0f;
		this.lastTickTime = currentTime;
		
		return new GameTime(elapsedTime, totalTime);
	}
	private void menuTick(GameTime gameTime, Renderer renderer) {
		this.menuScene.update(gameTime);
		this.menuScene.draw(gameTime, renderer);

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
			// We should throw excetion here.
		}
	}
	private void gameTick(GameTime gameTime, Renderer renderer) {
		this.gameScene.update(gameTime);
		this.gameScene.draw(gameTime, renderer);

		GameSceneAction gameAction = this.gameScene.getAction();
		switch (gameAction) {
		case Quit:
			this.state = ContinuityGameState.Menu;
			break;
		default:
			// We should throw excetion here.
		}
	}
}
