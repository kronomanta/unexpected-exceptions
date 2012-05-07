package game.level;

import java.util.Map;

import main.KeyboardState;


import game.Constants;
import game.GameTime;
import game.IDrawableGameComponent;
import game.renderer.Image;
import game.renderer.Point;
import game.renderer.Renderer;
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
		this.image = Image.loadFromFile("data/Player" + (isSecondPlayer ? "2" : "1") + ".png");		// texture location
		this.isMovingLeft = false;

		IBounds bounds = this.player.getBounds();							// set player component size
		this.width = bounds.getWidth() * Constants.UnitSize;
		this.height = bounds.getHeight() * Constants.UnitSize;

		this.idleRight = new SpriteAnimation(0, 0, 1);							// set idle player 1 sprite image
		this.runRight = new SpriteAnimation(0, 1, 6);							// set running player 1 sprite animation
		this.jumpRight = new SpriteAnimation(0, 7, 1);							// set jumping player 1 sprite image
		this.fallRight = new SpriteAnimation(0, 8, 1);							// set falling player 1 sprite image

		this.idleLeft = new SpriteAnimation(1, 0, 1);							// set idle player 2 sprite image
		this.runLeft = new SpriteAnimation(1, 1, 6);							// set running player 2 sprite animation
		this.jumpLeft = new SpriteAnimation(1, 7, 1);							// set jumping player 2 sprite image
		this.fallLeft = new SpriteAnimation(1, 8, 1);							// set falling player 2 sprite image
		
		this.animationPlayer = new SrpitePlayer(this.image, 81, 81, 0.05f);				// send image to animate with size and dt (time) parameters
	}

	// Public methods
	@Override
	public void draw(GameTime gameTime, Renderer renderer) {
		this.animationPlayer.draw(gameTime, renderer, new Point(this.x - 16.0f, this.y - 8.0f));	// call to SrpitePlayer's draw function
	}

	@Override
	public void update(GameTime gameTime) {
		Boolean moveLeft = false;
		Boolean moveRight = false;
		Boolean jump = false;

		if (this.isEnabled) {										// handle player controls
			moveLeft = KeyboardState.getInstance().isKeyDown(this.isSecondPlayer ? "A" : "Left");
			moveRight = KeyboardState.getInstance().isKeyDown(this.isSecondPlayer ? "D" : "Right");
			if (moveLeft)
				this.isMovingLeft = true;
			else if (moveRight)
				this.isMovingLeft = false;
			
			jump = KeyboardState.getInstance().isKeyDown(this.isSecondPlayer ? "W" : "Up");
			this.player.update(gameTime.getElapsedTime(), moveLeft, moveRight, jump);
		}

		LevelPart currentLevelPart = this.player.getLevelPart();					// which levelpart is it located in
		LevelPartComponent currentLpc = this.levelPartComponents.get(currentLevelPart);
		IBounds bounds = this.player.getBounds();
		this.x = currentLpc.getCurrentX() + bounds.getLeft() * Constants.UnitSize;			// where is it located inside the levelpart
		this.y = currentLpc.getCurrentY() + bounds.getTop() * Constants.UnitSize;
		this.centerX = this.x + this.width / 2.0f;							// set center of player's rectangle
		this.centerY = this.y + this.height / 2.0f;
		
		applyAnimations();
	}
    private void applyAnimations()
    {
    	Boolean isOnGround = this.player.getIsOnGround();							// set animation frame modes
    	Vector2 velocity = this.player.getVelocity();
        if (isOnGround)												// is it on the ground?
        {
            if (velocity.getX() != 0)										// does it have a vertical velocity?
                animationPlayer.play(this.isMovingLeft ? runLeft : runRight);					// is it running to the left or to the right?
            else
                animationPlayer.play(this.isMovingLeft ? idleLeft : idleRight);					// is it looking to the left or to the right?
        }
        else
        {
            if (velocity.getY() > 0)										// is the horizontal velocity positive?
                animationPlayer.play(this.isMovingLeft ? fallLeft : fallRight);					// then it's falling, either facing the left or the right
            else if (velocity.getY() < 0)									// is the horizontal velocity negative?
                animationPlayer.play(this.isMovingLeft ? jumpLeft : jumpRight);					// then it's jumping, either facing the left or the right
        }
    }
}
