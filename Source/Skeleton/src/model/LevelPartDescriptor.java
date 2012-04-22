package model;


import main.SkeletonHelper;

import org.w3c.dom.Element;


public class LevelPartDescriptor {
	private int x;
	private int y;
	private BlockDescriptor[] blocks;
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public BlockDescriptor[] getBlocks() {
		return this.blocks;
	}
	
	public LevelPartDescriptor(int x, int y, BlockDescriptor[] blocks) {
		this.x = x;
		this.y = y;
		this.blocks = blocks;
	}
	
	public static LevelPartDescriptor parse(Element levelPartElement) {
		SkeletonHelper.enterMethod();
		BlockDescriptor.parse(null);
		
		return new LevelPartDescriptor(0, 0, new BlockDescriptor[0]);
	}
}
