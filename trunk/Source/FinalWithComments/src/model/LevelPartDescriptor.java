package model;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

// descriptor class for level parts (aka puzzle pieces)
public class LevelPartDescriptor {
	private int x;				// level part position
	private int y;
	private BlockDescriptor[] blocks;	// container array for blocks aka walls
	
	// Getters and setters
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	// Constructor
	public BlockDescriptor[] getBlocks() {
		return this.blocks;
	}
	
	public LevelPartDescriptor(int x, int y, BlockDescriptor[] blocks) {
		this.x = x;
		this.y = y;
		this.blocks = blocks;
	}
	
	// Parser to read from file
	public static LevelPartDescriptor parse(Element levelPartElement) {
		int x = Integer.parseInt(levelPartElement.getAttribute("X"));
		int y = Integer.parseInt(levelPartElement.getAttribute("Y"));
		
		NodeList nodes = levelPartElement.getElementsByTagName("Block");
		BlockDescriptor[] blocks = new BlockDescriptor[nodes.getLength()];
		for (int i = 0; i < nodes.getLength(); i++) {
			blocks[i] = BlockDescriptor.parse((Element)nodes.item(i));
		}
		
		return new LevelPartDescriptor(x, y, blocks);
	}
}
