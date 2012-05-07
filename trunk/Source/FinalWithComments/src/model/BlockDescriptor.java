package model;

import org.w3c.dom.Element;

// Description of blocks
public class BlockDescriptor {
	private int x;						// start position
	private int y;
	private int width;					// block size
	private int height;
	private BlockType type;					// type can either be rectangle or right ramped triangle or left ramped triangle
	
	// Getters and setters
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public BlockType getType() {
		return this.type;
	}
	
	// Constructor
	public BlockDescriptor(int x, int y, int width, int height, BlockType type) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.type = type;
	}
	
	// Parser to read from file
	public static BlockDescriptor parse(Element blockElement) {
		int x = Integer.parseInt(blockElement.getAttribute("X"));
		int y = Integer.parseInt(blockElement.getAttribute("Y"));
		int width = Integer.parseInt(blockElement.getAttribute("Width"));
		int height = Integer.parseInt(blockElement.getAttribute("Height"));
		String typeString = blockElement.getAttribute("Type");	
		BlockType type = typeString == "" ? BlockType.Normal : BlockType.valueOf(typeString);

		return new BlockDescriptor(x, y, width, height, type);
	}
}
