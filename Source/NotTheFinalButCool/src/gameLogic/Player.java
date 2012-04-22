package gameLogic;

import java.util.Map;

import model.LevelObjectDescriptor;
import model.LevelObjectType;

public class Player extends GameObject {
	private static final float Width = 1.0f;
	private static final float Height = 1.0f;
	private static final float GravityAcceleration = 30.0f;
	private static final float HorizontalSpeed = 5.0f;
	private static final float JumpLaunchVelocity = -12.0f;

	private LevelObjectDescriptor spawnPoint;
	private LevelPart spawnLevelPart;
	private float logicalX;
	private float logicalY;
	private float verticalSpeed;

	private Boolean isOnGround;
	private PlayerJumpState jumpState;

	public float getLogicalX() {
		return this.logicalX;
	}

	public float getLogicalY() {
		return this.logicalY;
	}

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

	public void update(float time, Boolean goLeft, Boolean goRight, Boolean jump) {
		float movement = 0.0f;
		if (goLeft && !goRight)
			movement = -1.0f;
		else if (!goLeft && goRight)
			movement = 1.0f;

		this.logicalX += movement * HorizontalSpeed * time;
		this.verticalSpeed += GravityAcceleration * time;
		handleJump(jump);
		this.logicalY += this.verticalSpeed * time;

		handleLevelPartBoundaries();
		updateLocation();
		handleCollisions();
	}

	private void handleJump(Boolean jump) {
		if (this.jumpState == PlayerJumpState.CanJump) {
			if (jump) {
				if (this.isOnGround) {
					this.verticalSpeed = JumpLaunchVelocity;
					this.jumpState = PlayerJumpState.CannotJump;
				} else {
					this.jumpState = PlayerJumpState.WaitsForJump;
				}
			}
		} else if (this.jumpState == PlayerJumpState.CannotJump) {
			if (!jump) {
				this.jumpState = PlayerJumpState.CanJump;
			}
		} else if (this.jumpState == PlayerJumpState.WaitsForJump) {
			if (this.isOnGround) {
				this.verticalSpeed = JumpLaunchVelocity;
				this.jumpState = PlayerJumpState.CannotJump;
			}
		}
	}
	
	private void handleCollisions() {
		this.isOnGround = false;
		
		Block[] blocks = this.getLevelPart().getBlocks();
		for (int i = 0; i < blocks.length; i++) {
			Block block = blocks[i];
			IBounds blockBounds = block.getBounds();
			Vector2 depth = blockBounds.getIntersectionDepthWith((RectangleBounds)this.bounds);
			if (depth != null) {
				float absDepthX = Math.abs(depth.getX());
                float absDepthY = Math.abs(depth.getY());
				
                if (absDepthX < absDepthY) {
                	this.logicalX -= depth.getX();
                } else {
                	if (depth.getY() >= 0.0f) {
                		if (this.verticalSpeed < 0.0f)
                			continue;
                		
                        this.isOnGround = true;
                        this.verticalSpeed = 0.0f;
                	}
                
                	this.logicalY -= depth.getY();
                }
            	updateLocation();
			}
		}
	}

	private void handleLevelPartBoundaries() {
		LevelPart levelPart = this.getLevelPart();
		Map<Direction, Boolean> passabilities = levelPart.getPassabilities();
		Map<Direction, LevelPart> neighbours = levelPart.getNeighbours();

		if (this.logicalY > LevelPart.Size) {
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

	private void resetToSpawnPoint() {
		this.setLevelPart(this.spawnLevelPart);
		this.logicalX = this.spawnPoint.getX() + 0.5f;
		this.logicalY = this.spawnPoint.getY() + 1.0f - Height / 2.0f;
		this.verticalSpeed = 0.0f;
		this.jumpState = PlayerJumpState.CannotJump;
	}

	private void updateLocation() {
		this.setBounds(new RectangleBounds(this.logicalX - Width / 2.0f,
				this.logicalY - Height / 2.0f, Width, Height));
	}
}
