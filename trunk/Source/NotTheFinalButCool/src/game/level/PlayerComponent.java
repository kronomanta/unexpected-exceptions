package game.level;

import engine.Color;
import engine.DrawableGameComponent;
import engine.GameTime;
import engine.Renderer;
import game.Constants;
import game.level.LevelScene.State;
import gameLogic.IBounds;
import gameLogic.Player;
import gameLogic.LevelPart;

public class PlayerComponent extends DrawableGameComponent {
	private static final Color player1Color = new Color(255, 0, 0);
	private static final Color player2Color = new Color(0, 0, 255);

	private Player player;
	private Boolean isSecondPlayer;
	private LevelScene scene;

	private int x;
	private int y;
	private int width;
	private int height;
	private int centerX;
	private int centerY;

	public int getCenterX() {
		return this.centerX;
	}

	public int getCenterY() {
		return this.centerY;
	}

	public PlayerComponent(Player player, Boolean isSecondPlayer, LevelScene scene) {
		this.player = player;
		this.isSecondPlayer = isSecondPlayer;
		this.scene = scene;
		
		IBounds bounds = this.player.getBounds();
		this.width = Math.round(bounds.getWidth() * Constants.UnitSize);
		this.height = Math.round(bounds.getHeight() * Constants.UnitSize);
	}

	@Override
	public void draw(GameTime gameTime, Renderer renderer) {
		renderer.setTransform(this.scene.getCamera().getTransform());
		renderer.drawRectangle(this.x, this.y, this.width, this.height, this.isSecondPlayer ? player2Color
				: player1Color);
	}

	@Override
	public void update(GameTime gameTime) {
		Boolean moveLeft = false;
		Boolean moveRight = false;
		Boolean jump = false;
		
		if (this.scene.getState() == State.Playing) {
			moveLeft = this.getKeyboardState().isKeyDown(this.isSecondPlayer ? "A" : "Left");
			moveRight = this.getKeyboardState().isKeyDown(this.isSecondPlayer ? "D" : "Right");
			jump = this.getKeyboardState().isKeyDown(this.isSecondPlayer ? "W" : "Up");
			this.player.update(gameTime.getElapsedTime(), moveLeft, moveRight, jump);
		}

		LevelPart currentLevelPart = this.player.getLevelPart();
		LevelPartComponent currentLpc = this.scene.getLevelPartComponents().get(currentLevelPart);
		IBounds bounds = this.player.getBounds();
		this.x = Math.round(currentLpc.getCurrentX() + bounds.getLeft() * Constants.UnitSize);
		this.y = Math.round(currentLpc.getCurrentY() + bounds.getTop() * Constants.UnitSize);
		this.centerX = this.x + this.width / 2;
		this.centerY = this.y + this.height / 2;
	}
}
