package game.level;

import model.BlockType;
import engine.GameComponent;
import engine.GameTime;
import engine.SwingRenderer;
import game.Constants;

import gameLogic.Vector2;
import gameLogic.Block;
import gameLogic.IBounds;
import gameLogic.LevelPart;

public class LevelPartComponent extends GameComponent {

	private static final int positionStep = Constants.LevelPartSize + 
			2 * Constants.LevelPartBorderThickness + 
			Constants.LevelPartSpacing;

	private LevelPart levelPart;
	private LevelScene scene;

	private int levelPartX;
	private int levelPartY;
	private float currentX;
	private float currentY;
	private float startX;
	private float startY;
	private float targetX;
	private float targetY;
	private float animationStartTime;
	private Boolean animationRunning;

	public float getCurrentX() {
		return this.currentX;
	}

	public float getCurrentY() {
		return this.currentY;
	}

	public LevelPartComponent(LevelPart levelPart, LevelScene scene) {
		this.levelPart = levelPart;
		this.scene = scene;

		this.levelPartX = this.levelPart.getX();
		this.levelPartY = this.levelPart.getY();
		this.currentX = positionStep * this.levelPartX;
		this.currentY = positionStep * this.levelPartY;
		this.animationRunning = false;
	}

	@Override
	public void draw(GameTime gameTime, SwingRenderer renderer) {
		renderer.setTransform(this.scene.getCamera().getTransform());

		for (Block block : this.levelPart.getBlocks()) {
			IBounds bounds = block.getBounds();
			int x = Math.round(this.currentX + bounds.getLeft() * Constants.UnitSize);
			int y = Math.round(this.currentY + bounds.getTop() * Constants.UnitSize);
			int width = Math.round(bounds.getWidth()) * Constants.UnitSize;
			int height = Math.round(bounds.getHeight()) * Constants.UnitSize;

			Vector2 topLeft = new Vector2(x, y);
			Vector2 topRight = new Vector2(x + width, y);
			Vector2 bottomRight = new Vector2(x + width, y + height);
			Vector2 bottomLeft = new Vector2(x, y + height);

			if (block.getType() == BlockType.Normal)
				renderer.drawPolygon(new Vector2[] { topLeft, topRight, bottomRight, bottomLeft }, Constants.BlockColor);
			else if (block.getType() == BlockType.LeftRamp)
				renderer.drawPolygon(new Vector2[] { topRight, bottomRight, bottomLeft }, Constants.BlockColor);
			else if (block.getType() == BlockType.RightRamp)
				renderer.drawPolygon(new Vector2[] { topLeft, bottomRight, bottomLeft }, Constants.BlockColor);
		}
	}

	@Override
	public void update(GameTime gameTime) {
		float totalTime = gameTime.getTotalTime();
		if (this.animationRunning) {
			float progress = (totalTime - this.animationStartTime) / Constants.LevelPartSlidingDuration;
			if (progress > 1.0f) {
				this.currentX = this.targetX;
				this.currentY = this.targetY;
				this.animationRunning = false;
			} else {
				progress = 1 - (progress - 1) * (progress - 1);

				this.currentX = this.startX + progress * (this.targetX - this.startX);
				this.currentY = this.startY + progress * (this.targetY - this.startY);
			}
		}

		if (this.levelPartX != this.levelPart.getX() || this.levelPartY != this.levelPart.getY()) {
			this.levelPartX = this.levelPart.getX();
			this.levelPartY = this.levelPart.getY();
			this.startX = currentX;
			this.startY = currentY;
			this.targetX = positionStep * this.levelPartX;
			this.targetY = positionStep * this.levelPartY;
			this.animationStartTime = gameTime.getTotalTime();
			this.animationRunning = true;
		}
	}
}
