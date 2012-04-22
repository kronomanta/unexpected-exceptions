package engine;

import java.util.ArrayList;

public abstract class Game {
	private ArrayList<Scene> scenes;
	private Scene activeScene;

	private Renderer renderer;
	private ContentManager contentManager;
	private KeyboardState keyboardState;
	private IViewport viewport;

	private Boolean firstTick = true;
	private long firstTickTime;
	private long lastTickTime;

	public Scene getActiveScene() {
		return this.activeScene;
	}

	protected void setActiveScene(Scene scene) {
		this.activeScene = scene;
	}

	protected Game(Renderer renderer, IContentProvider contentProvider, KeyboardState keyboardState, IViewport viewport) {
		this.scenes = new ArrayList<Scene>();

		this.renderer = renderer;
		this.contentManager = new ContentManager(contentProvider);
		this.keyboardState = keyboardState;
		this.viewport = viewport;
	}

	protected void addScene(Scene scene) {
		scene.internalInitialize(this.contentManager, this.keyboardState, this.viewport);
		scene.initialize();
		this.scenes.add(scene);
	}

	public void tick() {
		long currentTime = System.currentTimeMillis();

		if (firstTick) {
			this.firstTickTime = currentTime;
			this.lastTickTime = currentTime;

			firstTick = false;
		}

		float elapsedTime = (currentTime - this.lastTickTime) / 1000.0f;
		float totalTime = (currentTime - this.firstTickTime) / 1000.0f;
		this.lastTickTime = currentTime;
		GameTime gameTime = new GameTime(elapsedTime, totalTime);

		this.activeScene.update(gameTime);
		this.activeScene.draw(gameTime, renderer);
		this.keyboardState.tick();
	}
}
