package game.level;

import java.util.Map;

import game.Constants;
import game.GameTime;
import game.IDrawableGameComponent;
import game.renderer.Image;
import game.renderer.Renderer;
import gameLogic.Door;
import gameLogic.IBounds;
import gameLogic.LevelPart;

public class DoorComponent implements IDrawableGameComponent {
	// Private fields
	private Image image;
	private Map<LevelPart, LevelPartComponent> levelPartComponents;
	private Door door;

	// Constructors
	public DoorComponent(Door door, Map<LevelPart, LevelPartComponent> levelPartComponents) {
		this.image = Image.loadFromFile("data/Door.png");					// texture location
		this.levelPartComponents = levelPartComponents;						// set levelpart component
		this.door = door;
	}

	// Public methods
	@Override
	public void draw(GameTime gameTime, Renderer renderer) {					// draw this item
		LevelPart currentLevelPart = this.door.getLevelPart();					// which levelpart is it located in
		LevelPartComponent currentLpc = this.levelPartComponents.get(currentLevelPart);
		IBounds bounds = this.door.getBounds();
		float x = currentLpc.getCurrentX() + bounds.getLeft() * Constants.UnitSize;		// where is it located inside the levelpart
		float y = currentLpc.getCurrentY() + bounds.getTop() * Constants.UnitSize;

		renderer.drawImage(x, y, this.image);							// call to renderer's drawimage function
	}
	@Override
	public void update(GameTime gameTime) {								// nothing to update
	}
}
