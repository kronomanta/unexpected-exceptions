package game.level;

import java.util.Map;

import main.KeyboardState;

import renderer.Image;
import renderer.Point;
import renderer.Renderer;

import game.Constants;
import game.GameTime;
import game.IDrawableGameComponent;
import gameLogic.IBounds;
import gameLogic.Player;
import gameLogic.LevelPart;
import gameLogic.Vector2;

public class PlayerComponent implements IDrawableGameComponent {
	// Private fields
	private Player player;
	private Boolean isSecondPlayer;
	private Map<LevelPart, LevelPartComponent> levelPartComponents;
	private Boolean isEnabled;
	private Image image;

	private float x;
	private float y;
	private float width;
	private float height;
	private float centerX;
	private float centerY;
	private Boolean isMovingLeft;

	private SpriteAnimation idleRight;
	private SpriteAnimation runRight;
	private SpriteAnimation jumpRight;
	private SpriteAnimation fallRight;

	private SpriteAnimation idleLeft;
	private SpriteAnimation runLeft;
	private SpriteAnimation jumpLeft;
	private SpriteAnimation fallLeft;
	
	private SrpitePlayer animationPlayer;

	// Getters and setters
	public float getCenterX() {
		return this.centerX;
	}

	public float getCenterY() {
		return this.centerY;
	}

	public Boolean getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	// Constructors
	public PlayerComponent(Player player, Boolean isSecondPlayer, Map<LevelPart, LevelPartComponent> levelPartComponents) {
		this.player = player;
		this.isSecondPlayer = isSecondPlayer;
		this.levelPartComponents = levelPartComponents;
		this.isEnabled = true;
		this.image = Image.loadFromFile("data/Player" + (isSecondPlayer ? "2" : "1") + ".png");
		this.isMovingLeft = false;

		IBounds bounds = this.player.getBounds();
		this.width = bounds.getWidth() * Constants.UnitSize;
		this.height = bounds.getHeight() * Constants.UnitSize;

		this.idleRight = new SpriteAnimation(0, 0, 1);
		this.runRight = new SpriteAnimation(0, 1, 6);
		this.jumpRight = new SpriteAnimation(0, 7, 1);
		this.fallRight = new SpriteAnimation(0, 8, 1);

		this.idleLeft = new SpriteAnimation(1, 0, 1);
		this.runLeft = new SpriteAnimation(1, 1, 6);
		this.jumpLeft = new SpriteAnimation(1, 7, 1);
		this.fallLeft = new SpriteAnimation(1, 8, 1);
		
		this.animationPlayer = new SrpitePlayer(this.image, 81, 81, 0.05f);
	}

	// Public methods
	@Override
	public void draw(GameTime gameTime, Renderer renderer) {
		this.animationPlayer.draw(gameTime, renderer, new Point(this.x - 16.0f, this.y - 8.0f));
	}

	@Override
	public void update(GameTime gameTime) {
		Boolean moveLeft = false;
		Boolean moveRight = false;
		Boolean jump = false;

		if (this.isEnabled) {
			moveLeft = KeyboardState.getInstance().isKeyDown(this.isSecondPlayer ? "A" : "Left");
			moveRight = KeyboardState.getInstance().isKeyDown(this.isSecondPlayer ? "D" : "Right");
			if (moveLeft)
				this.isMovingLeft = true;
			else if (moveRight)
				this.isMovingLeft = false;
			
			jump = KeyboardState.getInstance().isKeyDown(this.isSecondPlayer ? "W" : "Up");
			this.player.update(gameTime.getElapsedTime(), moveLeft, moveRight, jump);
		}

		LevelPart currentLevelPart = this.player.getLevelPart();
		LevelPartComponent currentLpc = this.levelPartComponents.get(currentLevelPart);
		IBounds bounds = this.player.getBounds();
		this.x = currentLpc.getCurrentX() + bounds.getLeft() * Constants.UnitSize;
		this.y = currentLpc.getCurrentY() + bounds.getTop() * Constants.UnitSize;
		this.centerX = this.x + this.width / 2.0f;
		this.centerY = this.y + this.height / 2.0f;
		
		applyAnimations();
	}
    private void applyAnimations()
    {
    	Boolean isOnGround = this.player.getIsOnGround();
    	Vector2 velocity = this.player.getVelocity();
        if (isOnGround)
        {
            if (velocity.getX() != 0)
                animationPlayer.play(this.isMovingLeft ? runLeft : runRight);
            else
                animationPlayer.play(this.isMovingLeft ? idleLeft : idleRight);
        }
        else
        {
            if (velocity.getY() > 0)
                animationPlayer.play(this.isMovingLeft ? fallLeft : fallRight);
            else if (velocity.getY() < 0)
                animationPlayer.play(this.isMovingLeft ? jumpLeft : jumpRight);
        }
    }
}
