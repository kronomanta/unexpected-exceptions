package game.level;

import engine.DrawableGameComponent;
import engine.GameTime;
import engine.Renderer;
import game.Constants;

public class LevelPartBorderComponent extends DrawableGameComponent {
	private LevelPartComponent lpc;
	private LevelScene scene;

	public LevelPartBorderComponent(LevelPartComponent lpc, LevelScene scene) {
		this.lpc = lpc;
		this.scene = scene;
	}

	@Override
	public void draw(GameTime gameTime, Renderer renderer) {
		renderer.setTransform(this.scene.getCamera().getTransform());

		int x = Math.round(this.lpc.getCurrentX()) - Constants.LevelPartBorderThickness;
		int y = Math.round(this.lpc.getCurrentY()) - Constants.LevelPartBorderThickness;
		int size = 2 * Constants.LevelPartBorderThickness + Constants.LevelPartSize;

		int sideY = y + Constants.LevelPartBorderThickness;
		int sideHeight = size - 2 * Constants.LevelPartBorderThickness;

		renderer.drawRectangle(x, y, size, Constants.LevelPartBorderThickness, Constants.LevelPartBorderColor);
		renderer.drawRectangle(x, y + size - Constants.LevelPartBorderThickness, size,
				Constants.LevelPartBorderThickness, Constants.LevelPartBorderColor);
		renderer.drawRectangle(x, sideY, Constants.LevelPartBorderThickness, sideHeight, Constants.LevelPartBorderColor);
		renderer.drawRectangle(x + size - Constants.LevelPartBorderThickness, sideY,
				Constants.LevelPartBorderThickness, sideHeight, Constants.LevelPartBorderColor);
	}

	@Override
	public void update(GameTime gameTime) {
	}

}
