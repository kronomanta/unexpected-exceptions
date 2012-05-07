package game.level;

import java.util.Map;

import game.Constants;
import game.GameTime;
import game.IDrawableGameComponent;
import game.renderer.Image;
import game.renderer.Renderer;
import gameLogic.IBounds;
import gameLogic.KeyHolder;
import gameLogic.LevelPart;

public class KeyComponent implements IDrawableGameComponent {
	private Image image;
	private Map<LevelPart, LevelPartComponent> levelPartComponents;
	private KeyHolder keyHolder;

	public KeyComponent(KeyHolder keyHolder, Map<LevelPart, LevelPartComponent> levelPartComponents) {
		this.image = Image.loadFromFile("data/Key.png");					// texture location
		this.levelPartComponents = levelPartComponents;						// set levelpart components
		this.keyHolder = keyHolder;
	}

	@Override
	public void draw(GameTime gameTime, Renderer renderer) {					// draw this item
		if (this.keyHolder.getHasKey()) {							// if it isn't picked up already
			LevelPart currentLevelPart = this.keyHolder.getLevelPart();			// which levelpart is it located in
			LevelPartComponent currentLpc = this.levelPartComponents.get(currentLevelPart);
			IBounds bounds = this.keyHolder.getBounds();
			float x = currentLpc.getCurrentX() + bounds.getLeft() * Constants.UnitSize;	// where is it located inside the levelpart
			float y = currentLpc.getCurrentY() + bounds.getTop() * Constants.UnitSize;

			renderer.drawImage(x, y, this.image);						// call renderer's drawimage function
		}
	}

	@Override
	public void update(GameTime gameTime) {								// nothing to update
	}
}
