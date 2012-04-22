package game.level;

import engine.Color;
import engine.DrawableGameComponent;
import engine.GameTime;
import engine.Renderer;
import game.Constants;
import gameLogic.Door;
import gameLogic.IBounds;
import gameLogic.LevelPart;

public class DoorComponent extends DrawableGameComponent {
	private static final Color doorColor = new Color(0, 0, 0);
	
	private Door door;
	private LevelScene scene;

	public DoorComponent(Door door, LevelScene scene) {
		this.door = door;
		this.scene = scene;
	}
	
	@Override
	public void draw(GameTime gameTime, Renderer renderer) {
		LevelPart currentLevelPart = this.door.getLevelPart();
		LevelPartComponent currentLpc = this.scene.getLevelPartComponents().get(currentLevelPart);
		IBounds bounds = this.door.getBounds();
		float x = Math.round(currentLpc.getCurrentX() + bounds.getLeft() * Constants.UnitSize);
		float y = Math.round(currentLpc.getCurrentY() + bounds.getTop() * Constants.UnitSize);
		
		renderer.setTransform(this.scene.getCamera().getTransform());
		renderer.drawRectangle(x, y, Constants.UnitSize, Constants.UnitSize, doorColor);
	}

	@Override
	public void update(GameTime gameTime) {
	}
}