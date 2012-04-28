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
		this.image = Image.loadFromFile("data/Door.png");
		this.levelPartComponents = levelPartComponents;
		this.door = door;
	}

	// Public methods
	@Override
	public void draw(GameTime gameTime, Renderer renderer) {
		LevelPart currentLevelPart = this.door.getLevelPart();
		LevelPartComponent currentLpc = this.levelPartComponents.get(currentLevelPart);
		IBounds bounds = this.door.getBounds();
		float x = currentLpc.getCurrentX() + bounds.getLeft() * Constants.UnitSize;
		float y = currentLpc.getCurrentY() + bounds.getTop() * Constants.UnitSize;

		renderer.drawImage(x, y, this.image);
	}
	@Override
	public void update(GameTime gameTime) {
	}
}
