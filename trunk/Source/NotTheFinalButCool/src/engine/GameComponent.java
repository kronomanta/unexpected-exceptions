package engine;

public abstract class GameComponent {
	private ContentManager contentManager;
	private KeyboardState keyboardState;
	private IViewport viewport;
	
	public ContentManager getContentManager() {
		return this.contentManager;
	}

	public KeyboardState getKeyboardState() {
		return this.keyboardState;
	}

	public IViewport getViewport() {
		return this.viewport;
	}
	
	public void internalInitialize(ContentManager contentManager, KeyboardState keyboardState, IViewport viewport) {
		this.contentManager = contentManager;
		this.keyboardState = keyboardState;
		this.viewport = viewport;
	}
	
	public void initialize() {
	}

	public abstract void update(GameTime gameTime);
}
