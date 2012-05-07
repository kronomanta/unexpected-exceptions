package gameLogic;

import model.BlockDescriptor;
import model.BlockType;

// Block type objects represent impassable walls of given levelpart
public class Block extends GameObject {
	private BlockType type;			// type of block (can be rectangle, right ramp triangle or left ramp triangle)

	// Getters (blocks can be only set up by the level object parser)
	public BlockType getType() {
		return this.type;
	}

	// Constructor
	public Block(BlockDescriptor descriptor, LevelPart levelPart) {
		super(levelPart);
		this.type = descriptor.getType();
		IBounds bounds;
		if (this.type == BlockType.Normal)			// sets bounds according to the type of given block object
			bounds = new RectangleBounds(descriptor.getX(), descriptor.getY(),
					descriptor.getWidth(), descriptor.getHeight());
		else {
			bounds = new TriangleBounds(descriptor.getX(), descriptor.getY(),
					descriptor.getWidth(), descriptor.getHeight(),
					this.type == BlockType.LeftRamp ? TriangleType.BottomRight
							: TriangleType.BottomLeft);
		}
		this.setBounds(bounds);
	}
}
