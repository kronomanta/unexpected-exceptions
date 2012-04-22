package model;


import main.SkeletonHelper;

import org.w3c.dom.Element;

//Pályaelem leíró osztály
public class LevelPartDescriptor {
	//pozíció
	private int x;
	private int y;
	//Falak tárolása
	private BlockDescriptor[] blocks;
	
	//Állapotlekérdezõk.
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public BlockDescriptor[] getBlocks() {
		return this.blocks;
	}
	
	//Konst.
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
