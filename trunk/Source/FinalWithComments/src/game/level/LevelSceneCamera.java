package game.level;

import game.Constants;
import game.GameTime;
import game.IGameComponent;
import game.renderer.RenderTransform;

public class LevelSceneCamera implements IGameComponent {				// camera "lens" object through which we see the game
	// Private fields
	private PlayerComponent player1;
	private PlayerComponent player2;
	private int levelWidth;
	private int levelHeight;
	private LevelSceneCameraMode mode;

	private RenderTransform transform;

	private float currentTranslateX;
	private float currentTranslateY;
	private float currentScale;
	private float targetTranslateX;
	private float targetTranslateY;
	private float targetScale;

	// Getters and setters
	public RenderTransform getTransform() {
		return this.transform;
	}

	public void setMode(LevelSceneCameraMode mode) {
		this.mode = mode;
	}

	// Constructors
	public LevelSceneCamera(PlayerComponent player1, PlayerComponent player2, int levelWidth, int levelHeight) {
		this.player1 = player1;
		this.player2 = player2;
		this.levelWidth = levelWidth;
		this.levelHeight = levelHeight;
		this.mode = LevelSceneCameraMode.Distance;					// default state is puzzle mode (no following of separate players)

		this.transform = new RenderTransform();

		this.currentScale = 1.0f;							// no rescaling
		this.currentTranslateX = -Constants.LevelPartSize * levelWidth / 2.0f;		// set new X base position
		this.currentTranslateY = -Constants.LevelPartSize * levelHeight * 2;		// set new Y base position
	}

	// Public methods
	@Override
	public void update(GameTime gameTime) {							// update camera lens position
		float targetWidth;
		float targetHeight;
		float targetX;
		float targetY;
		float maximumScale = Float.MAX_VALUE;

		if (this.mode == LevelSceneCameraMode.SlideUp) {
			this.targetScale = this.currentScale;					// keep current scaling
			this.targetTranslateX = this.currentTranslateX;				// no new X base position
			this.targetTranslateY = this.currentTranslateY - Constants.LevelPartSize * levelHeight * 2;	// slide Y base position upwards
		} else if (this.mode == LevelSceneCameraMode.SlideDown) {
			this.targetScale = this.currentScale;					// keep current scaling
			this.targetTranslateX = this.currentTranslateX;				// no new X base position
			this.targetTranslateY = this.currentTranslateY + Constants.LevelPartSize * levelHeight * 2;	// slide Y base position downwards
		} else {
			if (this.mode == LevelSceneCameraMode.Follow) {
				targetWidth = Math.abs(this.player1.getCenterX() - this.player2.getCenterX()) + Constants.UnitSize;	// get zoom size so both
				targetHeight = Math.abs(this.player1.getCenterY() - this.player2.getCenterY()) + Constants.UnitSize;	// players will be on screen
				targetX = (this.player1.getCenterX() + this.player2.getCenterX()) / 2.0f;					// set new X position
				targetY = (this.player1.getCenterY() + this.player2.getCenterY()) / 2.0f - Constants.CanvasHeight / 6.0f;	// set new y position
				maximumScale = Constants.LevelSceneCameraPlayingMaximumScale;						// set maximum scale
			} else {
				int totalLevelPartSize = Constants.LevelPartSize + 2 * Constants.LevelPartBorderThickness;		// otherwise in puzzle mode
																	// adjust zoom size to levelpart borders
				targetWidth = this.levelWidth * totalLevelPartSize + (this.levelWidth - 1) * Constants.LevelPartSpacing;	// adjust zoom size
				targetHeight = this.levelHeight * totalLevelPartSize + (this.levelHeight - 1) * Constants.LevelPartSpacing;
				targetX = targetWidth / 2.0f - Constants.LevelPartBorderThickness;					// set new X position
				targetY = targetHeight / 2.0f - Constants.LevelPartBorderThickness;					// set new y position
			}

			float scaleX = (Constants.CanvasWidth - 2 * Constants.LevelSceneCameraViewportMarginRate) / targetWidth;	// set scaling
			float scaleY = (Constants.CanvasHeight - 2 * Constants.LevelSceneCameraViewportMarginRate) / targetHeight;
			this.targetScale = Math.min(Math.min(scaleX, scaleY), maximumScale);						// don't scale over maximum scale

			this.targetTranslateX = Constants.CanvasWidth / 2.0f / this.targetScale - targetX;
			this.targetTranslateY = Constants.CanvasHeight / 2.0f / this.targetScale - targetY;
		}

		float x = 1 - (float) Math.pow(0.01f, gameTime.getElapsedTime());					// modify camera position and zoom according to elapsed time
		this.currentTranslateX = this.currentTranslateX + (this.targetTranslateX - this.currentTranslateX) * x;	// set final translations
		this.currentTranslateY = this.currentTranslateY + (this.targetTranslateY - this.currentTranslateY) * x;
		this.currentScale = this.currentScale + (this.targetScale - this.currentScale) * x;			// set final scaling

		this.transform.setTranslateX(this.currentTranslateX);							// call translations
		this.transform.setTranslateY(this.currentTranslateY);
		this.transform.setScale(this.currentScale);								// call scaling
	}
}
