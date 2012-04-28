package game.level;

import java.util.Map;

import renderer.Image;
import renderer.Renderer;
import game.Constants;
import game.GameTime;
import game.IDrawableGameComponent;
import gameLogic.IBounds;
import gameLogic.KeyHolder;
import gameLogic.LevelPart;

public class KeyComponent implements IDrawableGameComponent {
	private Image image;
	private Map<LevelPart, LevelPartComponent> levelPartComponents;
	private KeyHolder keyHolder;

	public KeyComponent(KeyHolder keyHolder, Map<LevelPart, LevelPartComponent> levelPartComponents) {
		this.image = Image.loadFromFile("data/Key.png");
		this.levelPartComponents = levelPartComponents;
		this.keyHolder = keyHolder;
	}

	@Override
	public void draw(GameTime gameTime, Renderer renderer) {
		if (this.keyHolder.getHasKey()) {
			LevelPart currentLevelPart = this.keyHolder.getLevelPart();
			LevelPartComponent currentLpc = this.levelPartComponents.get(currentLevelPart);
			IBounds bounds = this.keyHolder.getBounds();
			float x = currentLpc.getCurrentX() + bounds.getLeft() * Constants.UnitSize;
			float y = currentLpc.getCurrentY() + bounds.getTop() * Constants.UnitSize;

			renderer.drawImage(x, y, this.image);
		}
	}

	@Override
	public void update(GameTime gameTime) {
	}
}
