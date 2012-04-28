package game.level;

import game.Constants;
import game.GameTime;
import game.IGameComponent;
import game.renderer.RenderTransform;

public class LevelSceneCamera implements IGameComponent {
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
		this.mode = LevelSceneCameraMode.Distance;

		this.transform = new RenderTransform();

		this.currentScale = 1.0f;
		this.currentTranslateX = -Constants.LevelPartSize * levelWidth / 2.0f;
		this.currentTranslateY = -Constants.LevelPartSize * levelHeight * 2;
	}

	// Public methods
	@Override
	public void update(GameTime gameTime) {
		float targetWidth;
		float targetHeight;
		float targetX;
		float targetY;
		float maximumScale = Float.MAX_VALUE;

		if (this.mode == LevelSceneCameraMode.SlideUp) {
			this.targetScale = this.currentScale;
			this.targetTranslateX = this.currentTranslateX;
			this.targetTranslateY = this.currentTranslateY - Constants.LevelPartSize * levelHeight * 2;
		} else if (this.mode == LevelSceneCameraMode.SlideDown) {
			this.targetScale = this.currentScale;
			this.targetTranslateX = this.currentTranslateX;
			this.targetTranslateY = this.currentTranslateY + Constants.LevelPartSize * levelHeight * 2;
		} else {
			if (this.mode == LevelSceneCameraMode.Follow) {
				targetWidth = Math.abs(this.player1.getCenterX() - this.player2.getCenterX()) + Constants.UnitSize;
				targetHeight = Math.abs(this.player1.getCenterY() - this.player2.getCenterY()) + Constants.UnitSize;
				targetX = (this.player1.getCenterX() + this.player2.getCenterX()) / 2.0f;
				targetY = (this.player1.getCenterY() + this.player2.getCenterY()) / 2.0f - Constants.CanvasHeight / 6.0f;
				maximumScale = Constants.LevelSceneCameraPlayingMaximumScale;
			} else {
				int totalLevelPartSize = Constants.LevelPartSize + 2 * Constants.LevelPartBorderThickness;

				targetWidth = this.levelWidth * totalLevelPartSize + (this.levelWidth - 1) * Constants.LevelPartSpacing;
				targetHeight = this.levelHeight * totalLevelPartSize + (this.levelHeight - 1) * Constants.LevelPartSpacing;
				targetX = targetWidth / 2.0f - Constants.LevelPartBorderThickness;
				targetY = targetHeight / 2.0f - Constants.LevelPartBorderThickness;
			}

			float scaleX = (Constants.CanvasWidth - 2 * Constants.LevelSceneCameraViewportMarginRate) / targetWidth;
			float scaleY = (Constants.CanvasHeight - 2 * Constants.LevelSceneCameraViewportMarginRate) / targetHeight;
			this.targetScale = Math.min(Math.min(scaleX, scaleY), maximumScale);

			this.targetTranslateX = Constants.CanvasWidth / 2.0f / this.targetScale - targetX;
			this.targetTranslateY = Constants.CanvasHeight / 2.0f / this.targetScale - targetY;
		}

		float x = 1 - (float) Math.pow(0.01f, gameTime.getElapsedTime());
		this.currentTranslateX = this.currentTranslateX + (this.targetTranslateX - this.currentTranslateX) * x;
		this.currentTranslateY = this.currentTranslateY + (this.targetTranslateY - this.currentTranslateY) * x;
		this.currentScale = this.currentScale + (this.targetScale - this.currentScale) * x;

		this.transform.setTranslateX(this.currentTranslateX);
		this.transform.setTranslateY(this.currentTranslateY);
		this.transform.setScale(this.currentScale);
	}
}
