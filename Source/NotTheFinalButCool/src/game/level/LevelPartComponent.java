package game.level;

import model.BlockType;
import game.Constants;
import game.GameTime;
import game.IDrawableGameComponent;
import game.renderer.Point;
import game.renderer.Renderer;
import gameLogic.Block;
import gameLogic.IBounds;
import gameLogic.LevelPart;

public class LevelPartComponent implements IDrawableGameComponent {
	// Constants
	private static final int positionStep = Constants.LevelPartSize + 2 * Constants.LevelPartBorderThickness + Constants.LevelPartSpacing;

	// Private fields
	private LevelPart levelPart;

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

	// Getters and setters
	public float getCurrentX() {
		return this.currentX;
	}

	public float getCurrentY() {
		return this.currentY;
	}

	// Constructors
	public LevelPartComponent(LevelPart levelPart) {
		this.levelPart = levelPart;

		this.levelPartX = this.levelPart.getX();
		this.levelPartY = this.levelPart.getY();
		this.currentX = positionStep * this.levelPartX;
		this.currentY = positionStep * this.levelPartY;
		this.animationRunning = false;
	}

	// Public methods
	@Override
	public void draw(GameTime gameTime, Renderer renderer) {
		for (Block block : this.levelPart.getBlocks()) {
			IBounds bounds = block.getBounds();
			int x = Math.round(this.currentX + bounds.getLeft() * Constants.UnitSize);
			int y = Math.round(this.currentY + bounds.getTop() * Constants.UnitSize);
			int width = Math.round(bounds.getWidth()) * Constants.UnitSize;
			int height = Math.round(bounds.getHeight()) * Constants.UnitSize;

			Point topLeft = new Point(x - 2.0f, y - 2.0f);
			Point topRight = new Point(x + width + 2.0f, y - 2.0f);
			Point bottomRight = new Point(x + width + 2.0f, y + height + 2.0f);
			Point bottomLeft = new Point(x - 2.0f, y + height + 2.0f);

			if (block.getType() == BlockType.Normal)
				renderer.drawPolygon(new Point[] { topLeft, topRight, bottomRight, bottomLeft }, Constants.BlockColor);
			else if (block.getType() == BlockType.LeftRamp)
				renderer.drawPolygon(new Point[] { topRight, bottomRight, bottomLeft }, Constants.BlockColor);
			else if (block.getType() == BlockType.RightRamp)
				renderer.drawPolygon(new Point[] { topLeft, bottomRight, bottomLeft }, Constants.BlockColor);
		}
	}
	public void drawBorder(Renderer renderer) {
		float x = this.currentX - Constants.LevelPartBorderThickness;
		float y = this.currentY - Constants.LevelPartBorderThickness;
		float size = 2 * Constants.LevelPartBorderThickness + Constants.LevelPartSize;

		float sideY = y + Constants.LevelPartBorderThickness;
		float sideHeight = size - 2 * Constants.LevelPartBorderThickness;

		renderer.drawRectangle(x, y, size, Constants.LevelPartBorderThickness, Constants.LevelPartBorderColor);
		renderer.drawRectangle(x, y + size - Constants.LevelPartBorderThickness, size, Constants.LevelPartBorderThickness, Constants.LevelPartBorderColor);
		renderer.drawRectangle(x, sideY, Constants.LevelPartBorderThickness, sideHeight, Constants.LevelPartBorderColor);
		renderer.drawRectangle(x + size - Constants.LevelPartBorderThickness, sideY, Constants.LevelPartBorderThickness, sideHeight, Constants.LevelPartBorderColor);
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
