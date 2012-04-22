package game.level;

import java.awt.Color;
import engine.GameComponent;
import engine.GameTime;
import engine.SwingRenderer;
import game.Constants;
import gameLogic.Door;
import gameLogic.IBounds;
import gameLogic.LevelPart;

public class DoorComponent extends GameComponent {
	private static final Color doorColor = new Color(0, 0, 0);
	
	private Door door;
	private LevelScene scene;

	public DoorComponent(Door door, LevelScene scene) {
		this.door = door;
		this.scene = scene;
	}
	
	@Override
	public void draw(GameTime gameTime, SwingRenderer renderer) {
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