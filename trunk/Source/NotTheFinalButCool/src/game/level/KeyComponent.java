package game.level;

import engine.Color;
import engine.DrawableGameComponent;
import engine.GameTime;
import engine.Renderer;
import game.Constants;
import gameLogic.IBounds;
import gameLogic.KeyHolder;
import gameLogic.LevelPart;

public class KeyComponent extends DrawableGameComponent {
	private static final Color keyColor = new Color(255, 255, 0);
	private static final Color noKeyColor = new Color(255, 255, 200);
	
	private KeyHolder keyHolder;
	private LevelScene scene;

	public KeyComponent(KeyHolder keyHolder, LevelScene scene) {
		this.keyHolder = keyHolder;
		this.scene = scene;
	}
	
	@Override
	public void draw(GameTime gameTime, Renderer renderer) {
		LevelPart currentLevelPart = this.keyHolder.getLevelPart();
		LevelPartComponent currentLpc = this.scene.getLevelPartComponents().get(currentLevelPart);
		IBounds bounds = this.keyHolder.getBounds();
		float x = Math.round(currentLpc.getCurrentX() + bounds.getLeft() * Constants.UnitSize);
		float y = Math.round(currentLpc.getCurrentY() + bounds.getTop() * Constants.UnitSize);
		
		Color color = this.keyHolder.getHasKey() ? keyColor : noKeyColor;
		
		renderer.setTransform(this.scene.getCamera().getTransform());
		renderer.drawRectangle(x, y, Constants.UnitSize, Constants.UnitSize, color);
	}

	@Override
	public void update(GameTime gameTime) {
	}
}
