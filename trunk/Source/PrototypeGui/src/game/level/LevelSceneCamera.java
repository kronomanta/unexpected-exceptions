package game.level;

import engine.GameComponent;
import engine.GameTime;
import engine.SwingRenderer;

import javax.swing.JFrame;
import engine.RenderTransform;
import game.Constants;
import game.level.LevelScene.State;

public class LevelSceneCamera extends GameComponent {
	private PlayerComponent player1;
	private PlayerComponent player2;
	private LevelScene scene;
	private JFrame viewport;

	private RenderTransform transform;

	private float currentTranslateX;
	private float currentTranslateY;
	private float currentScale;
	private float targetTranslateX;
	private float targetTranslateY;
	private float targetScale;

	public RenderTransform getTransform() {
		return this.transform;
	}

	public LevelSceneCamera(PlayerComponent player1, PlayerComponent player2, LevelScene scene, JFrame viewport) {
		this.player1 = player1;
		this.player2 = player2;
		this.scene = scene;
		this.viewport = viewport;
		this.transform = new RenderTransform();
	}

	@Override
	public void update(GameTime gameTime) {
		float targetWidth;
		float targetHeight;
		float targetX;
		float targetY;
		float maximumScale = Float.MAX_VALUE;
		
		if (this.scene.getState() == State.Sliding) {
			int totalLevelPartSize = Constants.LevelPartSize + 2 * Constants.LevelPartBorderThickness;
			
			int levelWidth = this.scene.getLevel().getWidth();
			int levelHeight = this.scene.getLevel().getHeight();
			
			targetWidth = levelWidth * totalLevelPartSize + (levelWidth - 1) * Constants.LevelPartSpacing;
			targetHeight = levelHeight * totalLevelPartSize + (levelHeight - 1) * Constants.LevelPartSpacing;
			targetX = targetWidth / 2.0f - Constants.LevelPartBorderThickness;
			targetY = targetHeight / 2.0f - Constants.LevelPartBorderThickness;
			
		} else {
			targetWidth = Math.abs(player1.getCenterX() - player2.getCenterX()) + Constants.UnitSize;
			targetHeight = Math.abs(player1.getCenterY() - player2.getCenterY()) + Constants.UnitSize;
			targetX = (player1.getCenterX() + player2.getCenterX()) / 2.0f;
			targetY = (player1.getCenterY() + player2.getCenterY()) / 2.0f - this.viewport.getHeight() / 6.0f;
			maximumScale = Constants.LevelSceneCameraPlayingMaximumScale;
		}

		float scaleX = (this.viewport.getWidth() - 2 * Constants.LevelSceneCameraViewportMarginRate) / targetWidth;
		float scaleY = (this.viewport.getHeight() - 2 * Constants.LevelSceneCameraViewportMarginRate) / targetHeight;
		this.targetScale = Math.min(Math.min(scaleX, scaleY), maximumScale);

		this.targetTranslateX = this.viewport.getWidth() / 2.0f / this.targetScale - targetX;
		this.targetTranslateY = this.viewport.getHeight() / 2.0f / this.targetScale - targetY;

		float x = 1 - (float) Math.pow(0.01f, gameTime.getElapsedTime());
		this.currentTranslateX = this.currentTranslateX + (this.targetTranslateX - this.currentTranslateX) * x;
		this.currentTranslateY = this.currentTranslateY + (this.targetTranslateY - this.currentTranslateY) * x;
		this.currentScale = this.currentScale + (this.targetScale - this.currentScale) * x;

		this.transform.setTranslateX(this.currentTranslateX);
		this.transform.setTranslateY(this.currentTranslateY);
		this.transform.setScale(this.currentScale);
	}

	@Override
	public void draw(GameTime gameTime, SwingRenderer renderer) {
	}
}
