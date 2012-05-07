package gameLogic;

import java.util.Map;

import model.LevelObjectDescriptor;
import model.LevelObjectType;

// Class responsible for player movement
public class Player extends GameObject {
	// Constants
	private static final float Width = 1.0f;			// player size
	private static final float Height = 1.0f;
	private static final float GravityAcceleration = 40.0f;		// gravity acceleration
	private static final float HorizontalSpeed = 7.0f;		// horizontal speed
	private static final float JumpLaunchVelocity = -13.0f;		// jump impulse velocity

	private LevelObjectDescriptor spawnPoint;			// position inside spawning levelpart
	private LevelPart spawnLevelPart;				// levelpart position
	private float logicalX;						// position
	private float logicalY;
	private float verticalSpeed;					// speed of player
	private float horizontalSpeed;

	private Boolean isOnGround;					// jump flags
	private PlayerJumpState jumpState;

	// Getters
	public float getLogicalX() {
		return this.logicalX;
	}

	public float getLogicalY() {
		return this.logicalY;
	}

	public Vector2 getVelocity() {
		return new Vector2(this.horizontalSpeed, this.verticalSpeed);
	}
	
	public Boolean getIsOnGround() {
		return this.isOnGround;
	}

	// Constructor	
	public Player(LevelPart levelPart, LevelObjectDescriptor spawnPoint) {
		super(levelPart);

		if (spawnPoint.getType() != LevelObjectType.Spawn)
			throw new IllegalArgumentException("Invalid object descriptor.");

		this.spawnLevelPart = levelPart;
		this.spawnPoint = spawnPoint;
		resetToSpawnPoint();
		updateLocation();
		
		handleCollisions();
		handleJump(false);
	}

	// Updates position and attributes of player according to elapsed time and given input
	public void update(float time, Boolean goLeft, Boolean goRight, Boolean jump) {
		float movement = 0.0f;					// clear garbage
		if (goLeft && !goRight)					// go left
			movement = -1.0f;
		else if (!goLeft && goRight)				// go right
			movement = 1.0f;

		this.horizontalSpeed = movement * HorizontalSpeed;	// calculates horizontal speed
		this.logicalX += this.horizontalSpeed * time;
		this.verticalSpeed += GravityAcceleration * time;	// calculates vertical speed
		handleJump(jump);
		this.logicalY += this.verticalSpeed * time;

		handleLevelPartBoundaries();
		updateLocation();
		handleCollisions();
	}

	// Handles jump according to basic mechanical dynamics
	private void handleJump(Boolean jump) {
		if (this.jumpState == PlayerJumpState.CanJump) {			// refreshes jump state
			if (jump) {							// if player wants to jump
				if (this.isOnGround) {					// checks if player is on ground
					this.verticalSpeed = JumpLaunchVelocity;	// player jumps
					this.jumpState = PlayerJumpState.CannotJump;	// refreshes jump state
				} else {						// otherwise if player is not on ground
					this.jumpState = PlayerJumpState.WaitsForJump;	// player waits for jump
				}
			}
		} else if (this.jumpState == PlayerJumpState.CannotJump) {		// if player cannot jump
			if (!jump) {							// and player didn't jump
				this.jumpState = PlayerJumpState.CanJump;		// player state will be CanJump
			}
		} else if (this.jumpState == PlayerJumpState.WaitsForJump) {		// if player waits for jump
			if (this.isOnGround) {						// as soon as player gets on the ground
				this.verticalSpeed = JumpLaunchVelocity;		// player jumps (once)
				this.jumpState = PlayerJumpState.CannotJump;		// refreshes jump state
			}
		}
	}
	
	// Handles collisions by either repositioning given player
	private void handleCollisions() {
		this.isOnGround = false;
		
		Block[] blocks = this.getLevelPart().getBlocks();
		for (int i = 0; i < blocks.length; i++) {				// checks if player's rectangle intersected with any of given levelpart's blocks
			Block block = blocks[i];
			IBounds blockBounds = block.getBounds();
			Vector2 depth = blockBounds.getIntersectionDepthWith((RectangleBounds)this.bounds);
			if (depth != null) {
				float absDepthX = Math.abs(depth.getX());		// if yes, it calculates given collision's x coordinate depth
                float absDepthY = Math.abs(depth.getY());				// it will always calculate y coordinate depth
				
                if (absDepthX < absDepthY) {						// if the x coordinate collision was smaller
                	this.logicalX -= depth.getX();					// then the collision handler will fix the player's x coordinate
                } else {								// otherwise
                	if (depth.getY() < 0.0f && this.verticalSpeed < 0.0f) {		// if player was falling
                		this.verticalSpeed = 0.0f;				// player stops falling
                	}
                	if (depth.getY() >= 0.0f) {					// or if player's "head" was colliding with a block 
                		if (this.verticalSpeed < 0.0f)				// yet player was falling
                			continue;					// then continue
                		
                        this.isOnGround = true;						// but if player and was on ground
                        this.verticalSpeed = 0.0f;					// set player's vertical speed to 0
                	}
                
                	this.logicalY -= depth.getY();					// finally the collision handler will fix the player's y coordinate
                }
            	updateLocation();							// then update player's location
			}
		}
	}

	// checks if player can move on to the next level part (nesting algorhythm)
	private void handleLevelPartBoundaries() {
		LevelPart levelPart = this.getLevelPart();
		Map<Direction, Boolean> passabilities = levelPart.getPassabilities();	// gets passabilities of given level
		Map<Direction, LevelPart> neighbours = levelPart.getNeighbours();	// gets neighbours of given levelpart

		if (this.logicalY > LevelPart.Size) {					// checks if given levelpart's neighbours' are passable
			if (passabilities.get(Direction.Down)) {
				this.setLevelPart(neighbours.get(Direction.Down));
				this.logicalY = 0.0f;
			} else {
				resetToSpawnPoint();
			}
		} else if (this.logicalY < 0.0f) {
			if (passabilities.get(Direction.Up)) {
				this.setLevelPart(neighbours.get(Direction.Up));
				this.logicalY = LevelPart.Size;
			} else {
				this.logicalY = 0.0f;
			}
		} else if (this.logicalX > LevelPart.Size) {
			if (passabilities.get(Direction.Right)) {
				this.setLevelPart(neighbours.get(Direction.Right));
				this.logicalX = 0.0f;
			} else {
				this.logicalX = LevelPart.Size;
			}
		} else if (this.logicalX < 0.0f) {
			if (passabilities.get(Direction.Left)) {
				this.setLevelPart(neighbours.get(Direction.Left));
				this.logicalX = LevelPart.Size;
			} else {
				this.logicalX = 0.0f;
			}
		}
	}

	// gets called when player dies
	private void resetToSpawnPoint() {
		this.setLevelPart(this.spawnLevelPart);				// sets player position to the spawn point's level part
		this.logicalX = this.spawnPoint.getX() + 0.5f;			// sets player location to spawn point
		this.logicalY = this.spawnPoint.getY() + 1.0f - Height / 2.0f;
		this.verticalSpeed = 0.0f;					// clears player's movement attributes
		this.jumpState = PlayerJumpState.CannotJump;
	}

	// updates player's location
	private void updateLocation() {
		this.setBounds(new RectangleBounds(this.logicalX - Width / 2.0f,
				this.logicalY - Height / 2.0f, Width, Height));
	}
}
