package gameLogic;

import model.BlockDescriptor;
import model.BlockType;

public class Block extends GameObject {
	private BlockType type;

	public BlockType getType() {
		return this.type;
	}

	public Block(BlockDescriptor descriptor, LevelPart levelPart) {
		super(levelPart);
		this.type = descriptor.getType();
		IBounds bounds;
		if (this.type == BlockType.Normal)
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
